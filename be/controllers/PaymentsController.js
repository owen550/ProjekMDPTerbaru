const { Payments, Users, ActivityLogs } = require("../models/index");
const midtransClient = require('midtrans-client');
const axios = require('axios');

const snap = new midtransClient.Snap({
    isProduction: false,
    serverKey: 'Mid-server-WpdVl7Iuga96MRP31IeKtMK3'
});

const checkPaymentStatusAxios = async (req, res) => {
    try {
        const { orderId } = req.params;

        const serverKey = 'Mid-server-WpdVl7Iuga96MRP31IeKtMK3';
        const base64Key = Buffer.from(serverKey + ':').toString('base64');

        const response = await axios.get(
            `https://api.sandbox.midtrans.com/v2/${orderId}/status`,
            {
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                    'Authorization': `Basic ${base64Key}`
                }
            }
        );

        const statusResponse = response.data;
        const transactionStatus = statusResponse.transaction_status;
        const paymentType = statusResponse.payment_type;

        let localStatus = "pending";

        if (transactionStatus === 'settlement' || transactionStatus === 'capture') {
            localStatus = "settlement";
        } else if (transactionStatus === 'deny' || transactionStatus === 'cancel' || transactionStatus === 'expire') {
            localStatus = "failure";
        } else if (transactionStatus === 'pending') {
            localStatus = "pending";
        }

        const payment = await Payments.findOne({ where: { order_id: orderId } });
        
        if (payment) {
            payment.status = localStatus;
            payment.payment_method = paymentType;
            
            if (statusResponse.va_numbers && statusResponse.va_numbers.length > 0) {
                payment.va_number = statusResponse.va_numbers[0].va_number;
            }
            
            await payment.save();

            await ActivityLogs.create({
                user_id: payment.user_id,
                activity: `Pengecekan Axios memperbarui order ${orderId} menjadi ${localStatus}`,
                ip_address: req.ip,
            });
        }

        return res.status(200).json({
            status: "success",
            message: `Status order ${orderId} berhasil diperbarui`,
            data: payment
        });

    } catch (error) {
        return res.status(500).json({
            status: "error",
            message: error.response ? error.response.data : error.message
        });
    }
};

const createPaymentMidtrans = async (req, res) => {
    try {
        const userId = req.paymentUserId;
        const { amount } = req.body;

        if (!userId || !amount) {
            return res.status(400).json({
                status: "error",
                message: "user_id and amount are required"
            });
        }

        const generatedOrderId = `ORDER-${Date.now()}`;

        const parameter = {
            transaction_details: {
                order_id: generatedOrderId,
                gross_amount: Number(amount)
            },
            credit_card: {
                secure: true
            },
            customer_details: {
                first_name: `User ID ${userId}`,
                email: "user@example.com"
            }
        };

        const transaction = await snap.createTransaction(parameter);
        
        const newPayment = await Payments.create({
            user_id: Number(userId),
            order_id: generatedOrderId,
            payment_token: transaction.token,
            amount: Number(amount),
            payment_method: "midtrans_snap",
            va_number: null,
            qr_url: transaction.redirect_url,
            status: "pending"
        });

        await ActivityLogs.create({
            user_id: Number(userId),
            activity: `User id ${userId} membuat token pembayaran Midtrans order ${generatedOrderId}`,
            ip_address: req.ip,
        });

        res.status(200).json({
            status: "success",
            token: transaction.token,
            redirect_url: transaction.redirect_url,
            data: newPayment
        });
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
};

const midtransNotification = async (req, res) => {
    try {
        const statusResponse = await snap.transaction.notification(req.body);
        
        const orderId = statusResponse.order_id;
        const transactionStatus = statusResponse.transaction_status;
        const paymentType = statusResponse.payment_type;

        let localStatus = "pending";

        if (transactionStatus === 'settlement' || transactionStatus === 'capture') {
            localStatus = "settlement";
        } else if (transactionStatus === 'deny' || transactionStatus === 'cancel' || transactionStatus === 'expire') {
            localStatus = "failure";
        } else if (transactionStatus === 'pending') {
            localStatus = "pending";
        }

        const payment = await Payments.findOne({ where: { order_id: orderId } });
        
        if (payment) {
            payment.status = localStatus;
            payment.payment_method = paymentType;
            
            if (statusResponse.va_numbers && statusResponse.va_numbers.length > 0) {
                payment.va_number = statusResponse.va_numbers[0].va_number;
            }
            
            await payment.save();

            await ActivityLogs.create({
                user_id: payment.user_id,
                activity: `Webhook Midtrans memperbarui order ${orderId} menjadi ${localStatus}`,
                ip_address: req.ip,
            });
        }

        res.status(200).send('OK');
    } catch (error) {
        res.status(500).json({ error: error.message });
    }
};

const createPayment = async (req, res) => {
  try {
    const {
      order_id,
      payment_token,
      amount,
      payment_method,
      va_number,
      qr_url,
      status,
    } = req.body;
    const userId = req.paymentUserId;

    if (!userId || !order_id || !payment_token || !amount || !payment_method) {
      return res.status(400).json({
        status: "error",
        message:
          "user_id, order_id, payment_token, amount, and payment_method are required",
      });
    }

    const newPayment = await Payments.create({
      user_id: Number(userId),
      order_id,
      payment_token,
      amount,
      payment_method,
      va_number: va_number || null,
      qr_url: qr_url || null,
      status: status || "pending",
    });

    await ActivityLogs.create({
      user_id: Number(userId),
      activity: `User id ${userId} membuat pembayaran dengan order ${order_id}`,
      ip_address: req.ip,
    });

    return res.status(201).json({
      status: "success",
      data: newPayment,
    });
  } catch (error) {
    console.error("PaymentsController.createPayment", error);
    return res.status(500).json({
      status: "error",
      message: "Failed to create payment",
      details: error.message,
    });
  }
};

const getPaymentsByUser = async (req, res) => {
  try {
    const userId = req.paymentUserId;

    if (!userId) {
      return res.status(400).json({
        status: "error",
        message: "user_id is required",
      });
    }

    const payments = await Payments.findAll({
      where: {
        user_id: Number(userId),
      },
      order: [["created_at", "DESC"]],
    });

    return res.status(200).json({
      status: "success",
      data: payments,
    });
  } catch (error) {
    console.error("PaymentsController.getPaymentsByUser", error);
    return res.status(500).json({
      status: "error",
      message: "Failed to get payments",
      details: error.message,
    });
  }
};

const getAllPayments = async (req, res) => {
  try {
    const userId = req.paymentUserId;

    if (!userId) {
      return res.status(400).json({
        status: "error",
        message: "user_id is required",
      });
    }

    const payments = await Payments.findAll({
      order: [["created_at", "DESC"]],
    });

    await ActivityLogs.create({
      user_id: Number(userId),
      activity: `User id ${userId} melihat semua pembayaran`,
      ip_address: req.ip,
    });

    return res.status(200).json({
      status: "success",
      data: payments,
    });
  } catch (error) {
    console.error("PaymentsController.getAllPayments", error);
    return res.status(500).json({
      status: "error",
      message: "Failed to get all payments",
      details: error.message,
    });
  }
};

const getPaymentById = async (req, res) => {
  try {
    const paymentId = req.params.paymentId || req.params.id;
    const userId = req.paymentUserId;

    if (!paymentId || !userId) {
      return res.status(400).json({
        status: "error",
        message: "payment_id and user_id are required",
      });
    }

    const payment = await Payments.findOne({
      where: {
        id: Number(paymentId),
        user_id: Number(userId),
      },
    });

    if (!payment) {
      return res.status(404).json({
        status: "error",
        message: "Payment tidak ditemukan",
      });
    }

    return res.status(200).json({
      status: "success",
      data: payment,
    });
  } catch (error) {
    console.error("PaymentsController.getPaymentById", error);
    return res.status(500).json({
      status: "error",
      message: "Failed to get payment",
      details: error.message,
    });
  }
};

const updatePayment = async (req, res) => {
  try {
    const paymentId = req.params.paymentId || req.params.id;
    const userId = req.paymentUserId;
    const { status, payment_token, amount, payment_method, va_number, qr_url } =
      req.body;

    if (!paymentId || !userId) {
      return res.status(400).json({
        status: "error",
        message: "payment_id and user_id are required",
      });
    }

    const payment = await Payments.findOne({
      where: {
        id: Number(paymentId),
        user_id: Number(userId),
      },
    });

    if (!payment) {
      return res.status(404).json({
        status: "error",
        message: "Payment tidak ditemukan",
      });
    }

    if (status) payment.status = status;
    if (payment_token) payment.payment_token = payment_token;
    if (amount) payment.amount = amount;
    if (payment_method) payment.payment_method = payment_method;
    if (va_number !== undefined) payment.va_number = va_number;
    if (qr_url !== undefined) payment.qr_url = qr_url;

    await payment.save();

    await ActivityLogs.create({
      user_id: Number(userId),
      activity: `User id ${userId} memperbarui pembayaran id ${paymentId}`,
      ip_address: req.ip,
    });

    return res.status(200).json({
      status: "success",
      data: payment,
    });
  } catch (error) {
    console.error("PaymentsController.updatePayment", error);
    return res.status(500).json({
      status: "error",
      message: "Failed to update payment",
      details: error.message,
    });
  }
};

const deletePayment = async (req, res) => {
  try {
    const paymentId = req.params.paymentId || req.params.id;
    const userId = req.paymentUserId;

    if (!paymentId || !userId) {
      return res.status(400).json({
        status: "error",
        message: "payment_id and user_id are required",
      });
    }

    const payment = await Payments.findOne({
      where: {
        id: Number(paymentId),
        user_id: Number(userId),
      },
    });

    if (!payment) {
      return res.status(404).json({
        status: "error",
        message: "Payment tidak ditemukan",
      });
    }

    await payment.destroy();

    await ActivityLogs.create({
      user_id: Number(userId),
      activity: `User id ${userId} menghapus pembayaran id ${paymentId}`,
      ip_address: req.ip,
    });

    return res.status(200).json({
      status: "success",
      data: {
        id: Number(paymentId),
        deleted: true,
      },
    });
  } catch (error) {
    console.error("PaymentsController.deletePayment", error);
    return res.status(500).json({
      status: "error",
      message: "Failed to delete payment",
      details: error.message,
    });
  }
};

module.exports = {
  createPayment,
  getPaymentsByUser,
  getAllPayments,
  getPaymentById,
  updatePayment,
  deletePayment,
  createPaymentMidtrans,
  midtransNotification,
  checkPaymentStatusAxios
};
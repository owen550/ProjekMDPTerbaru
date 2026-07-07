const { Users } = require("../models/index");

const validatePaymentInput = async (req, res, next) => {
  try {
    const userId = req.params.userId || req.body.user_id || req.query.user_id;
    const { order_id, payment_token, amount, payment_method } = req.body;

    if (!userId) {
      return res.status(400).json({
        status: "error",
        message: "user_id is required",
      });
    }

    if (!order_id || !payment_token || !amount || !payment_method) {
      return res.status(400).json({
        status: "error",
        message:
          "order_id, payment_token, amount, and payment_method are required",
      });
    }

    const user = await Users.findByPk(Number(userId));
    if (!user) {
      return res.status(404).json({
        status: "error",
        message: `User dengan id ${userId} tidak ditemukan`,
      });
    }

    req.paymentUserId = Number(userId);
    next();
  } catch (error) {
    console.error("PaymentsMiddleware.validatePaymentInput", error);
    return res.status(500).json({
      status: "error",
      message: "Failed to validate payment input",
      details: error.message,
    });
  }
};

const validatePaymentAccess = async (req, res, next) => {
  try {
    const userId = req.params.userId || req.body.user_id || req.query.user_id;

    if (!userId) {
      return res.status(400).json({
        status: "error",
        message: "user_id is required",
      });
    }

    const user = await Users.findByPk(Number(userId));
    if (!user) {
      return res.status(404).json({
        status: "error",
        message: `User dengan id ${userId} tidak ditemukan`,
      });
    }

    req.paymentUserId = Number(userId);
    next();
  } catch (error) {
    console.error("PaymentsMiddleware.validatePaymentAccess", error);
    return res.status(500).json({
      status: "error",
      message: "Failed to validate payment access",
      details: error.message,
    });
  }
};

module.exports = {
  validatePaymentInput,
  validatePaymentAccess,
};

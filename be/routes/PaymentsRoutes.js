const express = require("express");
const router = express.Router();

const PaymentsController = require("../controllers/PaymentsController");
const PaymentsMiddleware = require("../middleware/PaymentsMiddleware");

router.post(
  "/midtrans-notification",
  PaymentsController.midtransNotification
);

router.get(
  "/midtrans-check/:orderId",
  PaymentsController.checkPaymentStatusAxios
);

router.post(
  "/:userId",
  PaymentsMiddleware.validatePaymentInput,
  PaymentsController.createPayment,
);

router.get(
  "/all/:userId",
  PaymentsMiddleware.validatePaymentAccess,
  PaymentsController.getAllPayments,
);

router.get(
  "/:userId",
  PaymentsMiddleware.validatePaymentAccess,
  PaymentsController.getPaymentsByUser,
);

router.get(
  "/:userId/:paymentId",
  PaymentsMiddleware.validatePaymentAccess,
  PaymentsController.getPaymentById,
);

router.put(
  "/:userId/:paymentId",
  PaymentsMiddleware.validatePaymentInput,
  PaymentsController.updatePayment,
);

router.delete(
  "/:userId/:paymentId",
  PaymentsMiddleware.validatePaymentAccess,
  PaymentsController.deletePayment,
);

router.post(
  "/midtrans/:userId",
  PaymentsMiddleware.validatePaymentAccess,
  PaymentsController.createPaymentMidtrans,
);

module.exports = router;
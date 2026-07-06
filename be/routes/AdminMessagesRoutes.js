const express = require("express");
const router = express.Router();

const AdminMessagesController = require("../controllers/AdminMessagesController");
const AdminMessagesMiddleware = require("../middleware/AdminMessagesMiddleware");

router.post(
  "/send/admin",
  AdminMessagesMiddleware.CekAdminMessageInput,
  AdminMessagesController.sendAdminMessage,
);

router.post(
  "/send/user",
  AdminMessagesMiddleware.CekUserMessageInput,
  AdminMessagesController.sendUserMessage,
);

router.get(
  "/getbyid/:userId/:adminId",
  AdminMessagesMiddleware.CekMessageAccessById,
  AdminMessagesController.getMessagesById,
);

router.get(
  "/adminchatlist/:adminId",
  AdminMessagesController.getUsersByAdminId,
);

module.exports = router;

const express = require("express");
const router = express.Router();

const CsChatbotChatsController = require("../controllers/CsChatbotChatsController");
const CsChatbotChatsMiddleware = require("../middleware/CsChatbotChatsMiddleware");

router.post(
  "/:userId",
  CsChatbotChatsMiddleware.validateChatInput,
  CsChatbotChatsController.createChat,
);

router.get(
  "/all/:userId",
  CsChatbotChatsMiddleware.validateChatAccess,
  CsChatbotChatsController.getAllChats,
);

router.get(
  "/:userId",
  CsChatbotChatsMiddleware.validateChatAccess,
  CsChatbotChatsController.getChatsByUser,
);

router.get(
  "/:userId/:chatId",
  CsChatbotChatsMiddleware.validateChatAccess,
  CsChatbotChatsController.getChatById,
);

router.put(
  "/:userId/:chatId",
  CsChatbotChatsMiddleware.validateChatInput,
  CsChatbotChatsController.updateChat,
);

router.delete(
  "/:userId/:chatId",
  CsChatbotChatsMiddleware.validateChatAccess,
  CsChatbotChatsController.deleteChat,
);

module.exports = router;

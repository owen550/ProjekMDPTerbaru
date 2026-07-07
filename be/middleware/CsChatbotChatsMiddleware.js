const { Users } = require("../models/index");

const validateChatInput = async (req, res, next) => {
  try {
    const userId = req.params.userId || req.body.user_id || req.query.user_id;
    const { sender, message } = req.body;

    if (!userId) {
      return res.status(400).json({
        status: "error",
        message: "user_id is required",
      });
    }

    if (!sender || !message) {
      return res.status(400).json({
        status: "error",
        message: "sender and message are required",
      });
    }

    const user = await Users.findByPk(Number(userId));
    if (!user) {
      return res.status(404).json({
        status: "error",
        message: `User dengan id ${userId} tidak ditemukan`,
      });
    }

    req.chatUserId = Number(userId);
    next();
  } catch (error) {
    console.error("CsChatbotChatsMiddleware.validateChatInput", error);
    return res.status(500).json({
      status: "error",
      message: "Failed to validate chat input",
      details: error.message,
    });
  }
};

const validateChatAccess = async (req, res, next) => {
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

    req.chatUserId = Number(userId);
    next();
  } catch (error) {
    console.error("CsChatbotChatsMiddleware.validateChatAccess", error);
    return res.status(500).json({
      status: "error",
      message: "Failed to validate chat access",
      details: error.message,
    });
  }
};

module.exports = {
  validateChatInput,
  validateChatAccess,
};

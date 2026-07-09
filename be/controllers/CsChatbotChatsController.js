const { CsChatbotChats, Users, ActivityLogs } = require("../models/index");

const createChat = async (req, res) => {
  try {
    const { sender, message } = req.body;
    const userId = req.chatUserId;

    if (!userId || !sender || !message) {
      return res.status(400).json({
        message: "user_id, sender, and message are required",
      });
    }

    const newChat = await CsChatbotChats.create({
      user_id: Number(userId),
      sender,
      message,
    });

    await ActivityLogs.create({
      user_id: Number(userId),
      activity: `User id ${userId} membuat chat chatbot dengan sender ${sender}`,
      ip_address: req.ip,
    });

    return res.status(201).json(newChat);
  } catch (error) {
    console.error("CsChatbotChatsController.createChat", error);
    return res.status(500).json({
      message: "Failed to create chat",
      details: error.message,
    });
  }
};

const getChatsByUser = async (req, res) => {
  try {
    const userId = req.chatUserId;

    if (!userId) {
      return res.status(400).json({
        message: "user_id is required",
      });
    }

    const chats = await CsChatbotChats.findAll({
      where: {
        user_id: Number(userId),
      },
      order: [["created_at", "ASC"]],
    });

    return res.status(200).json(chats);
  } catch (error) {
    console.error("CsChatbotChatsController.getChatsByUser", error);
    return res.status(500).json({
      message: "Failed to get chats",
      details: error.message,
    });
  }
};

const getAllChats = async (req, res) => {
  try {
    const userId = req.chatUserId;

    if (!userId) {
      return res.status(400).json({
        message: "user_id is required",
      });
    }

    const chats = await CsChatbotChats.findAll({
      order: [["created_at", "ASC"]],
    });

    await ActivityLogs.create({
      user_id: Number(userId),
      activity: `User id ${userId} melihat semua chat chatbot`,
      ip_address: req.ip,
    });

    return res.status(200).json(chats);
  } catch (error) {
    console.error("CsChatbotChatsController.getAllChats", error);
    return res.status(500).json({
      message: "Failed to get all chats",
      details: error.message,
    });
  }
};

const getChatById = async (req, res) => {
  try {
    const chatId = req.params.chatId || req.params.id;
    const userId = req.chatUserId;

    if (!chatId || !userId) {
      return res.status(400).json({
        message: "chat_id and user_id are required",
      });
    }

    const chat = await CsChatbotChats.findOne({
      where: {
        id: Number(chatId),
        user_id: Number(userId),
      },
    });

    if (!chat) {
      return res.status(404).json({
        message: "Chat tidak ditemukan",
      });
    }

    return res.status(200).json(chat);
  } catch (error) {
    console.error("CsChatbotChatsController.getChatById", error);
    return res.status(500).json({
      message: "Failed to get chat",
      details: error.message,
    });
  }
};

const updateChat = async (req, res) => {
  try {
    const chatId = req.params.chatId || req.params.id;
    const userId = req.chatUserId;
    const { message } = req.body;

    if (!chatId || !userId) {
      return res.status(400).json({
        message: "chat_id and user_id are required",
      });
    }

    if (!message) {
      return res.status(400).json({
        message: "message is required",
      });
    }

    const chat = await CsChatbotChats.findOne({
      where: {
        id: Number(chatId),
        user_id: Number(userId),
      },
    });

    if (!chat) {
      return res.status(404).json({
        message: "Chat tidak ditemukan",
      });
    }

    chat.message = message;
    await chat.save();

    await ActivityLogs.create({
      user_id: Number(userId),
      activity: `User id ${userId} mengubah chat chatbot id ${chatId}`,
      ip_address: req.ip,
    });

    return res.status(200).json(chat);
  } catch (error) {
    console.error("CsChatbotChatsController.updateChat", error);
    return res.status(500).json({
      message: "Failed to update chat",
      details: error.message,
    });
  }
};

const deleteChat = async (req, res) => {
  try {
    const chatId = req.params.chatId || req.params.id;
    const userId = req.chatUserId;

    if (!chatId || !userId) {
      return res.status(400).json({
        message: "chat_id and user_id are required",
      });
    }

    const chat = await CsChatbotChats.findOne({
      where: {
        id: Number(chatId),
        user_id: Number(userId),
      },
    });

    if (!chat) {
      return res.status(404).json({
        message: "Chat tidak ditemukan",
      });
    }

    await chat.destroy();

    await ActivityLogs.create({
      user_id: Number(userId),
      activity: `User id ${userId} menghapus chat chatbot id ${chatId}`,
      ip_address: req.ip,
    });

    return res.sendStatus(200);
  } catch (error) {
    console.error("CsChatbotChatsController.deleteChat", error);
    return res.status(500).json({
      message: "Failed to delete chat",
      details: error.message,
    });
  }
};

module.exports = {
  createChat,
  getChatsByUser,
  getAllChats,
  getChatById,
  updateChat,
  deleteChat,
};

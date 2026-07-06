const AdminMessages = require("../models/AdminMessages");
const { Users, ActivityLogs } = require("../models/index");

const sendAdminMessage = async (req, res) => {
  try {
    const { message_title, message_body } = req.body;
    const adminId = req.messageAdminId;
    const userId = req.messageUserId;

    if (!message_title || !message_body || !adminId || !userId) {
      return res.status(400).json({
        status: "error",
        message:
          "message_title, message_body, admin_id and user_id are required",
      });
    }

    const newMessage = await AdminMessages.create({
      admin_id: adminId,
      receiver_id: userId,
      message_title,
      message_body,
    });

    await ActivityLogs.create({
      user_id: adminId,
      activity: `Admin dengan id ${adminId} mengirim pesan baru kepada pengguna dengan id ${userId}`,
      ip_address: req.ip,
    });

    const responseData = newMessage.toJSON ? newMessage.toJSON() : newMessage;

    return res.status(201).json({
      status: "success",
      data: responseData,
    });
  } catch (error) {
    console.error("AdminMessagesController.sendAdminMessage", error);
    return res.status(500).json({
      status: "error",
      message: "Failed to send admin message",
      details: error.message,
    });
  }
};

const sendUserMessage = async (req, res) => {
  try {
    const { message_title, message_body } = req.body;
    const adminId = req.messageAdminId;
    const userId = req.messageUserId;

    if (!message_title || !message_body || !adminId || !userId) {
      return res.status(400).json({
        status: "error",
        message:
          "message_title, message_body, admin_id and user_id are required",
      });
    }

    const newMessage = await AdminMessages.create({
      admin_id: adminId,
      receiver_id: userId,
      message_title,
      message_body,
    });

    await ActivityLogs.create({
      user_id: userId,
      activity: `Pengguna denagn id ${userId}  mengirim pesan baru kepada admin dengan id ${adminId}`,
      ip_address: req.ip,
    });

    const responseData = newMessage.toJSON ? newMessage.toJSON() : newMessage;

    return res.status(201).json({
      status: "success",
      data: responseData,
    });
  } catch (error) {
    console.error("AdminMessagesController.sendUserMessage", error);
    return res.status(500).json({
      status: "error",
      message: "Failed to send user message",
      details: error.message,
    });
  }
};

const getMessagesById = async (req, res) => {
  try {
    const userId = req.messageUserId;
    const adminId = req.messageAdminId;

    if (!userId || !adminId) {
      return res.status(400).json({
        status: "error",
        message: "user_id and admin_id are required",
      });
    }

    const messages = await AdminMessages.findAll({
      where: {
        admin_id: Number(adminId),
        receiver_id: Number(userId),
      },
      order: [["created_at", "ASC"]],
    });

    const formattedMessages = messages.map((message) => {
      const payload = message.toJSON ? message.toJSON() : message;
      return payload;
    });

    await ActivityLogs.create({
      user_id: userId,
      activity: `Admin id ${adminId} membuka ruang percakapan dengan user ${userId}`,
      ip_address: req.ip,
    });

    return res.status(200).json({
      status: "success",
      data: formattedMessages,
    });
  } catch (error) {
    console.error("AdminMessagesController.getMessagesById", error);
    return res.status(500).json({
      status: "error",
      message: "Failed to get messages",
      details: error.message,
    });
  }
};

const getUsersByAdminId = async (req, res) => {
  try {
    const adminId =
      req.params.adminId || req.body.admin_id || req.query.admin_id;

    if (!adminId) {
      return res.status(400).json({
        status: "error",
        message: "admin_id is required",
      });
    }

    const admin = await Users.findByPk(adminId);
    if (!admin) {
      return res.status(404).json({
        status: "error",
        message: `Admin dengan id ${adminId} tidak ditemukan`,
      });
    }

    const messages = await AdminMessages.findAll({
      where: {
        admin_id: Number(adminId),
      },
      attributes: [
        "receiver_id",
        "created_at",
        "message_title",
        "message_body",
      ],
      order: [["created_at", "ASC"]],
    });

    const uniqueUsers = [];
    const seenUserIds = new Set();

    for (const message of messages) {
      const userId = message.receiver_id;
      if (!seenUserIds.has(userId)) {
        seenUserIds.add(userId);
        uniqueUsers.push({
          user_id: userId,
          last_message_at: message.created_at,
          last_message_title: message.message_title,
          last_message_body: message.message_body,
        });
      }
    }

    await ActivityLogs.create({
      user_id: adminId,
      activity: `Admin id ${adminId} membuka daftar chat dengan pengguna`,
      ip_address: req.ip,
    });

    return res.status(200).json({
      status: "success",
      data: uniqueUsers,
    });
  } catch (error) {
    console.error("AdminMessagesController.getUsersByAdminId", error);
    return res.status(500).json({
      status: "error",
      message: "Failed to get chat list",
      details: error.message,
    });
  }
};

module.exports = {
  sendAdminMessage,
  sendUserMessage,
  getMessagesById,
  getUsersByAdminId,
};

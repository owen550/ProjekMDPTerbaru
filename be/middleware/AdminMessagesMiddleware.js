const { Users } = require("../models/index");
const { Op } = require("sequelize");

const CekAdminMessageInput = async (req, res, next) => {
  try {
    const { admin_id, receiver_id, message_title, message_body } = req.body;

    if (!admin_id || !receiver_id || !message_title || !message_body) {
      return res.status(400).json({
        status: "error",
        message:
          "admin_id, receiver_id, message_title, and message_body are required",
      });
    }

    const sender = await Users.findOne({
      where: { id: admin_id, role: "admin" },
    });
    if (!sender) {
      return res.status(404).json({
        status: "error",
        message: `admin_id ${admin_id} tidak ditemukan atau bukan admin`,
      });
    }

    const receiver = await Users.findOne({
      where: { id: receiver_id, role: { [Op.notIn]: ["admin"] } },
    });
    if (!receiver) {
      return res.status(404).json({
        status: "error",
        message: `receiver_id ${receiver_id} tidak ditemukan atau bukan user`,
      });
    }

    if (Number(admin_id) === Number(receiver_id)) {
      return res.status(400).json({
        status: "error",
        message: "admin_id dan receiver_id tidak boleh sama",
      });
    }

    req.messageAdminId = Number(admin_id);
    req.messageUserId = Number(receiver_id);
    req.messageSenderRole = "admin";
    next();
  } catch (error) {
    console.error("AdminMessagesMiddleware.CekAdminMessageInput", error);
    return res.status(500).json({
      status: "error",
      message: "Failed to validate admin message input",
      details: error.message,
    });
  }
};

const CekUserMessageInput = async (req, res, next) => {
  try {
    const { user_id, admin_id, message_title, message_body } = req.body;

    if (!user_id || !admin_id || !message_title || !message_body) {
      return res.status(400).json({
        status: "error",
        message:
          "user_id, admin_id, message_title, and message_body are required",
      });
    }

    const sender = await Users.findOne({
      where: { id: user_id, role: { [Op.notIn]: ["admin"] } },
    });
    if (!sender) {
      return res.status(404).json({
        status: "error",
        message: `user_id ${user_id} tidak ditemukan atau bukan user`,
      });
    }

    const admin = await Users.findOne({
      where: { id: admin_id, role: "admin" },
    });
    if (!admin) {
      return res.status(404).json({
        status: "error",
        message: `admin_id ${admin_id} tidak ditemukan atau bukan admin`,
      });
    }

    if (Number(user_id) === Number(admin_id)) {
      return res.status(400).json({
        status: "error",
        message: "user_id dan admin_id tidak boleh sama",
      });
    }

    req.messageAdminId = Number(admin_id);
    req.messageUserId = Number(user_id);
    req.messageSenderRole = "user";
    next();
  } catch (error) {
    console.error("AdminMessagesMiddleware.CekUserMessageInput", error);
    return res.status(500).json({
      status: "error",
      message: "Failed to validate user message input",
      details: error.message,
    });
  }
};

const CekMessageAccessById = async (req, res, next) => {
  try {
    const userId =
      req.params.userId ||
      req.params.user_id ||
      req.body.user_id ||
      req.body.userId ||
      req.query.user_id ||
      req.query.userId;

    const adminId =
      req.params.adminId ||
      req.params.admin_id ||
      req.body.admin_id ||
      req.body.adminId ||
      req.query.admin_id ||
      req.query.adminId;

    if (!userId || !adminId) {
      return res.status(400).json({
        status: "error",
        message: "user_id and admin_id are required",
      });
    }

    const user = await Users.findByPk(userId);
    if (!user) {
      return res.status(404).json({
        status: "error",
        message: `User dengan id ${userId} tidak ditemukan`,
      });
    }

    const admin = await Users.findByPk(adminId);
    if (!admin) {
      return res.status(404).json({
        status: "error",
        message: `Admin dengan id ${adminId} tidak ditemukan`,
      });
    }

    req.messageUserId = Number(userId);
    req.messageAdminId = Number(adminId);
    next();
  } catch (error) {
    console.error("AdminMessagesMiddleware.CekMessageAccessById", error);
    return res.status(500).json({
      status: "error",
      message: "Failed to validate message access",
      details: error.message,
    });
  }
};

module.exports = {
  CekAdminMessageInput,
  CekUserMessageInput,
  CekMessageAccessById,
};

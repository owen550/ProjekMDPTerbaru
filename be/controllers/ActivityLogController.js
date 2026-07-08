// === semua model yang dipakai ===
require("dotenv").config();
const { ActivityLogs } = require("../models");

// ======================================================
// Get All Activity Logs
// ======================================================
exports.GetAllActivityLogs = async (req, res) => {
  try {

    // === ambil semua activity log ===
    let activitylogs = await ActivityLogs.findAll({
      order: [["created_at", "DESC"]]
    });

    // === return ===
    return res.status(200).json(activitylogs);

  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};

// ======================================================
// Get Activity Log By ID
// ======================================================
exports.GetActivityLogById = async (req, res) => {
  try {

    // === ambil id ===
    let { id } = req.body;

    // === cari activity log ===
    let activitylog = await ActivityLogs.findByPk(id);

    if (activitylog == null) {
      return res.status(400).json({
        message: `Activity Log dengan id ${id} tidak ditemukan`
      });
    }

    // === return ===
    return res.status(200).json(activitylog);

  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};

// ======================================================
// Delete Activity Log
// ======================================================
exports.DeleteActivityLog = async (req, res) => {
  try {

    // === ambil id ===
    let { id } = req.body;

    // === cari activity log ===
    let activitylog = await ActivityLogs.findByPk(id);

    if (activitylog == null) {
      return res.status(400).json({
        message: `Activity Log dengan id ${id} tidak ditemukan`
      });
    }

    let temp = activitylog;

    // === hapus ===
    await activitylog.destroy();

    // === return ===
    return res.status(200).json(temp);

  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};
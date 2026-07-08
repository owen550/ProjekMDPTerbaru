const express = require("express");
const router = express.Router();

const UsersMiddleware = require("../middleware/UsersMiddleware");
const ActivityLogsController = require("../controllers/ActivityLogsController");

// === Get Semua Activity Log ===
router.post(
    "/alldata",
    UsersMiddleware.CekUserIDTakKosong,
    UsersMiddleware.CekUserItuAda,
    UsersMiddleware.CekUserAdalahAdmin,
    ActivityLogsController.GetAllActivityLogs
);

// === Get Activity Log By ID ===
router.post(
    "/getbyid",
    UsersMiddleware.CekUserIDTakKosong,
    UsersMiddleware.CekUserItuAda,
    UsersMiddleware.CekUserAdalahAdmin,
    ActivityLogsController.GetActivityLogById
);

// === Hapus Activity Log ===
router.put(
    "/delete",
    UsersMiddleware.CekUserIDTakKosong,
    UsersMiddleware.CekUserItuAda,
    UsersMiddleware.CekUserAdalahAdmin,
    ActivityLogsController.DeleteActivityLog
);

module.exports = router;
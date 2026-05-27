const express = require("express");
const router = express.Router();
const tesController = require("../controllers/tescontroller.js");

// === ||| semua routes ||| ====
router.get("/data",tesController.tesapi);

// === ||| Export ||| ===
module.exports = router;
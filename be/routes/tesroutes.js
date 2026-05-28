const express = require("express");
const router = express.Router();
const tesController = require("../controllers/tescontroller.js");

// === ||| semua routes ||| ====
router.get("/data",tesController.tesapi);
router.post("/ai",tesController.tesai)

// === ||| Export ||| ===
module.exports = router;
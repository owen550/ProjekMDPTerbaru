const express = require("express");
const router = express.Router();
const UserMiddleware = require("../middleware/UsersMiddleware")
const UserController = require("../controllers/UsersController");


// === ||| semua routes ||| ====
router.post("/addusers",UserMiddleware.CekKelengkapanDataAddUser,UserController.AddUser);
router.get("/alldata",UserController.GetAllUser);

// === ||| Export ||| ===
module.exports = router;
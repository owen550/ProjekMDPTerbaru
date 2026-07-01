const express = require("express");
const router = express.Router();
const UserMiddleware = require("../middleware/UsersMiddleware")
const UserController = require("../controllers/UsersController");


// === ||| semua routes ||| ====
router.post("/addusers",UserMiddleware.CekKelengkapanDataAddUser,UserController.AddUser);
router.get("/alldata",UserController.GetAllUser);
router.post("/getdatabyid",UserController.GetUserById);
router.put("/updateuser",UserController.UpdateUserById);
router.put("/deleteuser",UserController.DeleteUser);

// === ||| Export ||| ===
module.exports = router;
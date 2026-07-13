const express = require("express");
const router = express.Router();
const UserMiddleware = require("../middleware/UsersMiddleware");
const UserController = require("../controllers/UsersController");

// === ||| semua routes ||| ====
router.post("/alldata", UserController.GetAllUser);
router.post("/getdatabyid", UserController.GetUserById);
router.put("/updateuser", UserController.UpdateUserById);
router.put("/deleteuser", UserController.DeleteUser);

router.post("/login", UserController.LoginUser);
router.post(
  "/addusers",
  UserMiddleware.CekKelengkapanDataAddUser,
  UserController.AddUser,
);
router.post("/google-auth", UserController.GoogleAuth);

// === ||| Export ||| ===
module.exports = router;

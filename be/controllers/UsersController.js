// === semua model yang dipakai ===
// const BahanBaku = require("../models/bahanBakuModel");

require("dotenv").config();
const {Users} = require("../models/index");
const bcrypt = require('bcrypt');

// === semua api ===
// exports.getAllBahanBaku = async (req, res) => {
//   try {
//     const bahanBakuList = await BahanBaku.findAll();
//     return res.status(200).json(bahanBakuList);
//   } catch (error) {
//     return res.status(500).json({ error: error.message });
//   }
// };

exports.GetAllUser = async (req, res) => {
  try {
    let getAllDataUser = await Users.findAll();
    return res.status(200).json(getAllDataUser);
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};

exports.AddUser = async (req, res) => {
  try { // ntik lewatin middleware dulu jangan lupa !!!
    const { name,username,password_user,email,google_id,birthday_date,role,tier } = req.body; // ntik dirubah
   
    // === proses password ===
    let passwordnow = password_user // password user sekarang
    let saltpassword = parseInt(process.env.SALTROUNDS) // panjang bcrypt
    const password = await bcrypt.hash(passwordnow,saltpassword) // enkripsinya
    // === cek jika ada nama user yang sama ? tolak 
    let allUserData = await Users.findOne({where: {username}})
    if(allUserData != null){
      return res.status(400).json({
        message: "Username Telah Dipakai !!!",
        status: '400',
      });
    }

    // === inputkan ke db jika lolos ===
    let newUser = await Users.create({name,username,password,email,google_id,birthday_date,role,tier});
    return res.status(200).json({
      message: "Berhasil Add User",
      status: '201',
      newUser
    });
  } catch (error) {
    return res.status(500).json({ 
      message: error.message,
      status: '500',
    });
  }
};
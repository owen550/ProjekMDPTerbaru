const jwt = require("jsonwebtoken");
require("dotenv").config();
const {Users} = require("../models/index");

let templateMiddleware = async (req, res, next) => {
  // template
  try {
    next();
  } catch (err) {
    return res.status(500).send({
      error: err.message,
    });
  }
};

let CekKelengkapanDataAddUser = async (req, res, next) => {
  try {
    const { name,username,password_user,email,google_id,birthday_date,role,tier } = req.body; // ntik dirubah
    if(!name || !email || !role ){
        return res.status(400).json({
          message: 'Kredensial Tidak Lengkap !!!',
          status: 400
        })
    }
    next();
  } catch (err) {
    return res.status(500).send({
      message: err.message,
      status: 500
    });
  }
};

// ngecek userid ada atau tidak
let CekUserIDTakKosong = async (req, res, next) => {
  // template
  try {
    let { userid } = req.body
    if(userid == null){
      return res.status(400).json({message: "ID User Tidak Valid"})
    }
    req.userid = userid
    next();
  } catch (err) {
    return res.status(500).send({
      error: err.message,
    });
  }
};

// ngecek apakah user ada di database atau tidak
let CekUserItuAda = async (req, res, next) => {
  // template
  try {
    // cek kevalidtan user
    let userid = req.userid

    // cek apkah user memang ada di database
    let cariuser = await Users.findByPk(userid)
    if(cariuser == null){
      return res.status(400).json({message: "ID User Tidak Ditemukan"})
    }
    req.user = cariuser
    next();
  } catch (err) {
    return res.status(500).send({
      error: err.message,
    });
  }
};

// cek role
let CekUserAdalahGuru = async (req, res, next) => {
  try {
    if(req.user == null){
      return res.status(400).json({message: "User Belum Dicari !!!"})
    }
    if(req.user.role != "teacher"){
      return res.status(403).json({message: "User Bukan Guru"})
    }
    next();
  } catch (err) {
    return res.status(500).send({
      error: err.message,
    });
  }
};
let CekUserAdalahAdmin = async (req, res, next) => {
  try {
    if(req.user == null){
      return res.status(400).json({message: "User Belum Dicari !!!"})
    }
    if(req.user.role != "admin"){
      return res.status(403).json({message: "User Bukan Admin"})
    }
    next();
  } catch (err) {
    return res.status(500).send({
      error: err.message,
    });
  }
};


module.exports = {
  CekKelengkapanDataAddUser, 
  CekUserIDTakKosong, // cek apakah userid ada
  CekUserItuAda, // cek apakah di db user, userid itu ada
  CekUserAdalahGuru, // cek apakah user adalah guru
  CekUserAdalahAdmin
}

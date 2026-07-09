const jwt = require("jsonwebtoken");
require("dotenv").config();
const {Courses} = require("../models/index");
const { where } = require("sequelize");

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

let CariCourseByID = async (req, res, next) => {
  try {
    // cek course id 
    let user = req.user
    let {courseid} = req.body
    if(courseid == null){
      return res.status(400).send({
        message: `Course ID tidak valid`,
      });
    }
    // cari database
    let finddatabase = await Courses.findByPk(courseid)
    if(finddatabase == null){
      return res.status(400).send({
        message: `Course dengan id ${courseid} tidak ditemukan`,
      });
    }
    // if(finddatabase.teacher_id != user.id){
    //   return res.status(400).send({
    //     message: `Tak Dapat Merubah,Anda Bukan Pembuat Course Ini`,
    //   });
    // }

    // simpan klo ketemu
    req.course = finddatabase
    next();
  } catch (err) {
    return res.status(500).send({
      error: err.message,
    });
  }
};

module.exports = {
  // CekKelengkapanDataAddUser // ntik ganti
  CariCourseByID
}

const jwt = require("jsonwebtoken");
require("dotenv").config();
const {CourseTopics} = require("../models/index");
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

let CekAttribut = async (req, res, next) => {
  try {
    let {topic_number,title,description,content_type} = req.body
    if(topic_number == null || title == null || description == null || content_type == null){
      return res.status(400).send({
        message: "Attribut Course Topic Tak Lengkap",
      });
    }
    next();
  } catch (err) {
    return res.status(500).send({
      error: err.message,
    });
  }
};

let CourseTopicFindByID = async (req, res, next) => {
  try {
    let { coursetopicid,title,description } = req.body
    // cek coursetopic
    if(coursetopicid == null){
      return res.status(400).send({
        message: "Course Topic Tidak Valid",
      });
    }
    // cari course topic
    let findcoursetopic = await CourseTopics.findByPk(coursetopicid)
    if(findcoursetopic == null){
      return res.status(400).send({
        message: "Course Topic Tidak Ditemukan",
      });
    }
    req.coursetopic = findcoursetopic
    next();
  } catch (err) {
    return res.status(500).send({
      error: err.message,
    });
  }
};

module.exports = {
  // CekKelengkapanDataAddUser // ntik ganti
  CekAttribut,
  CourseTopicFindByID
}

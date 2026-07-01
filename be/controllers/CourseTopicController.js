// course_type: ('material', 'quiz')

// === semua model yang dipakai ===
require("dotenv").config();
const { where } = require("sequelize");
const {CourseTopics,Courses,ActivityLogs} = require("../models/index");
const bcrypt = require('bcrypt');

exports.GetAllCourseTopicByID = async (req, res) => {
  try {
    // === find id user ===
    let user = req.user
    let course = req.course

    // === get all course topic === !!! MAIN CODE ON FUNCTION
    let getallbyid = await CourseTopics.findAll({
      where:{
        course_id: course.id
      }
    })

    // === activity log ===
    await ActivityLogs.create({
      user_id: user.id,
      activity: `Get All Course Topic`,
      ip_address: req.ip // cara dapetin ip clien
    })

    // === return ===
    return res.status(200).json(getallbyid);

  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};

// insert
exports.InsertCourseTopic = async (req, res) => {
  try {
    // === find id user ===
    let user = req.user
    let course = req.course
    let {topic_number,title,description,content_type} = req.body

    // === insert course topic === !!! MAIN CODE ON FUNCTION
    let isertCourseTopic = await CourseTopics.create({
      course_id : course.id,
      topic_number,
      title,
      description,
      content_type
    })

    // === activity log ===
    await ActivityLogs.create({
      user_id: user.id,
      activity: `Teacher menambahkan materi berjudul ${title}`,
      ip_address: req.ip // cara dapetin ip clien
    })

    // === return ===
    return res.status(200).json(isertCourseTopic);

  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};

// update
exports.UpdateCourseTopic = async (req, res) => {
  try {
    // === find id user ===
    let user = req.user
    let coursetopic = req.coursetopic

    // informasi tambahan
    let {description,title} = req.body

    // === update course topic === !!! MAIN CODE ON FUNCTION
    coursetopic.title = title
    coursetopic.description = description
    await coursetopic.save()

    // === activity log ===
    await ActivityLogs.create({
      user_id: user.id,
      activity: `Course Topic dengan id ${coursetopic.id} diupdate`,
      ip_address: req.ip // cara dapetin ip clien
    })

    // === return ===
    return res.status(200).json(coursetopic);

  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};

// delete
exports.DeleteCourseTopic = async (req, res) => {
  try {
    // === base information ===
    let coursetopic = req.coursetopic

    // === get all course topic === !!! MAIN CODE ON FUNCTION
    await coursetopic.destroy()

    // === activity log ===
    await ActivityLogs.create({
      user_id: user.id,
      activity: `course topic berjudul ${coursetopic.title} telah dihapus`,
      ip_address: req.ip // cara dapetin ip clien
    })

    // === return ===
    return res.status(200).json(coursetopic);

  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};
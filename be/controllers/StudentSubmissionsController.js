// === semua model yang dipakai ===
require("dotenv").config();
const { where } = require("sequelize");
const {
  StudentSubmissions,
  Quizzes,
  Users,
  ActivityLogs,
} = require("../models/index");

// =======================================================
// Get All Student Submissions
// =======================================================
exports.GetAllStudentSubmissions = async (req, res) => {
  try {
    // === find id user ===
    let { userid } = req.body;

    if (userid == null) {
      return res.status(400).json({ message: "ID Tidak Valid" });
    }

    let finduserbyid = await Users.findByPk(userid);

    if (finduserbyid == null) {
      return res.status(400).json({
        message: "User yang dicari tidak ketemu !!!",
      });
    }

    // === get all ===
    let allsubmission = await StudentSubmissions.findAll({
      where:{
        student_id: userid
      }
    });

    // === activity log ===
    await ActivityLogs.create({
      user_id: finduserbyid.id,
      activity: "Get All Student Submissions",
      ip_address: req.ip,
    });

    return res.status(200).json(allsubmission);
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};

// =======================================================
// Get Student Submission By ID
// =======================================================
exports.GetStudentSubmissionById = async (req, res) => {
  try {
    // === find id user ===
    let { userid, submissionid } = req.body;

    if (userid == null) {
      return res.status(400).json({ message: "ID Tidak Valid" });
    }

    let finduserbyid = await Users.findByPk(userid);

    if (finduserbyid == null) {
      return res.status(400).json({
        message: "User yang dicari tidak ketemu !!!",
      });
    }

    // === cari data ===
    let submission = await StudentSubmissions.findByPk(submissionid);

    if (submission == null) {
      return res.status(400).json({
        message: `Student Submission dengan id ${submissionid} tidak ditemukan`,
      });
    }

    // === activity log ===
    await ActivityLogs.create({
      user_id: finduserbyid.id,
      activity: "Get Student Submission By ID",
      ip_address: req.ip,
    });

    return res.status(200).json(submission);
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};

// =======================================================
// Insert
// =======================================================
exports.InsertStudentSubmission = async (req, res) => {
  try {
    // === tangkap data ===
    let {
      quiz_id,
      student_id,
      essay_answer,
      file_url,
      score,
      teacher_comment,
      status,
    } = req.body;

    let user = req.user;

    // === cek quiz ===
    let findquiz = await Quizzes.findByPk(quiz_id);

    if (findquiz == null) {
      return res.status(400).json({
        message: "Quiz Tidak Ditemukan",
      });
    }

    // === cek student ===
    let findstudent = await Users.findByPk(student_id);

    if (findstudent == null) {
      return res.status(400).json({
        message: "Student Tidak Ditemukan",
      });
    }

    // === insert ===
    let createsubmission = await StudentSubmissions.create({
      quiz_id,
      student_id,
      essay_answer,
      file_url,
      score,
      teacher_comment,
      status,
    });

    // === activity log ===
    await ActivityLogs.create({
      user_id: user.id,
      activity: `Membuat Student Submission Baru Dengan ID : ${createsubmission.id}`,
      ip_address: req.ip,
    });

    return res.status(200).json(createsubmission);
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};

// =======================================================
// Update
// =======================================================
exports.UpdateStudentSubmission = async (req, res) => {
  try {
    // === find user ===
    let user = req.user;

    let {
      id,
      essay_answer,
      file_url,
      score,
      teacher_comment,
      status,
    } = req.body;

    // === cari data ===
    let carisubmission = await StudentSubmissions.findByPk(id);

    if (carisubmission == null) {
      return res.status(400).json({
        message: "Student Submission Tidak Ketemu",
      });
    }

    // === update ===
    carisubmission.essay_answer = essay_answer;
    carisubmission.file_url = file_url;
    carisubmission.score = score;
    carisubmission.teacher_comment = teacher_comment;
    carisubmission.status = status;

    await carisubmission.save();

    // === activity log ===
    await ActivityLogs.create({
      user_id: user.id,
      activity: `Student Submission dengan id : ${id} telah dirubah`,
      ip_address: req.ip,
    });

    return res.status(200).json(carisubmission);
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};

// =======================================================
// Delete
// =======================================================
exports.DeleteStudentSubmission = async (req, res) => {
  try {
    // === find user ===
    let user = req.user;

    let { id } = req.body;

    // === cari data ===
    let carisubmission = await StudentSubmissions.findByPk(id);

    if (carisubmission == null) {
      return res.status(400).json({
        message: "Student Submission Tidak Ketemu",
      });
    }

    let temp = carisubmission;

    // === delete ===
    await carisubmission.destroy();

    // === activity log ===
    await ActivityLogs.create({
      user_id: user.id,
      activity: `Student Submission dengan id ${id} telah didelete`,
      ip_address: req.ip,
    });

    return res.status(200).json(temp);
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};
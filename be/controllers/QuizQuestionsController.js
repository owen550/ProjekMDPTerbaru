// === semua model yang dipakai ===
require("dotenv").config();
const { QuizQuestions, Quizzes, ActivityLogs, Users } = require("../models/index");

// =======================================================
// Get All Quiz Questions
// =======================================================
exports.GetAllQuizQuestions = async (req, res) => {
  try {
    // === find id user ===
    let { userid } = req.body;
    if (userid == null) {
      return res.status(400).json({ message: "ID Tidak Valid" });
    }

    let finduserbyid = await Users.findByPk(userid);
    if (finduserbyid == null) {
      return res.status(400).json({ message: "User yang dicari tidak ketemu !!!" });
    }

    // === get all quiz questions ===
    let allquizquestions = await QuizQuestions.findAll();

    // === activity log ===
    await ActivityLogs.create({
      user_id: finduserbyid.id,
      activity: "Get All Quiz Questions",
      ip_address: req.ip,
    });

    // === return ===
    return res.status(200).json(allquizquestions);
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};

// =======================================================
// Get Quiz Question By ID
// =======================================================
exports.GetQuizQuestionById = async (req, res) => {
  try {
    // === find id user ===
    let { userid, quizquestionid } = req.body;

    if (userid == null) {
      return res.status(400).json({ message: "ID Tidak Valid" });
    }

    let finduserbyid = await Users.findByPk(userid);
    if (finduserbyid == null) {
      return res.status(400).json({ message: "User yang dicari tidak ketemu !!!" });
    }

    // === get quiz question ===
    let quizquestion = await QuizQuestions.findByPk(quizquestionid);

    if (quizquestion == null) {
      return res.status(400).json({
        message: `Quiz Question dengan id ${quizquestionid} tidak ditemukan`,
      });
    }

    // === activity log ===
    await ActivityLogs.create({
      user_id: finduserbyid.id,
      activity: "Get Quiz Question By ID",
      ip_address: req.ip,
    });

    // === return ===
    return res.status(200).json(quizquestion);
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};

// =======================================================
// Insert Quiz Question
// =======================================================
exports.InsertQuizQuestion = async (req, res) => {
  try {
    // === tangkap data ===
    let { quiz_id, question_text, correct_answer } = req.body;
    let user = req.user;

    // === cek quiz ===
    let findquiz = await Quizzes.findByPk(quiz_id);
    if (findquiz == null) {
      return res.status(400).json({
        message: "Quiz Tidak Ditemukan",
      });
    }

    // === insert ===
    let createquizquestion = await QuizQuestions.create({
      quiz_id,
      question_text,
      correct_answer,
    });

    // === activity log ===
    await ActivityLogs.create({
      user_id: user.id,
      activity: `Membuat Quiz Question Baru Dengan ID : ${createquizquestion.id}`,
      ip_address: req.ip,
    });

    // === return ===
    return res.status(200).json(createquizquestion);
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};

// =======================================================
// Update Quiz Question
// =======================================================
exports.UpdateQuizQuestion = async (req, res) => {
  try {
    // === find id user ===
    let user = req.user;
    let { id, question_text, correct_answer } = req.body;

    // === cari quiz question ===
    let cariquizquestion = await QuizQuestions.findByPk(id);

    if (cariquizquestion == null) {
      return res.status(400).json({
        message: "Quiz Question Tidak Ketemu",
      });
    }

    // === update ===
    cariquizquestion.question_text = question_text;
    cariquizquestion.correct_answer = correct_answer;

    await cariquizquestion.save();

    // === activity log ===
    await ActivityLogs.create({
      user_id: user.id,
      activity: `Quiz Question dengan id : ${id} telah dirubah`,
      ip_address: req.ip,
    });

    // === return ===
    return res.status(200).json(cariquizquestion);
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};

// =======================================================
// Delete Quiz Question
// =======================================================
exports.DeleteQuizQuestion = async (req, res) => {
  try {
    // === find id user ===
    let user = req.user;
    let { id } = req.body;

    // === cari quiz question ===
    let cariquizquestion = await QuizQuestions.findByPk(id);

    if (cariquizquestion == null) {
      return res.status(400).json({
        message: "Quiz Question Tidak Ketemu",
      });
    }

    let temp = cariquizquestion;

    // === delete ===
    await cariquizquestion.destroy();

    // === activity log ===
    await ActivityLogs.create({
      user_id: user.id,
      activity: `Quiz Question dengan id ${id} telah didelete`,
      ip_address: req.ip,
    });

    // === return ===
    return res.status(200).json(temp);
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};
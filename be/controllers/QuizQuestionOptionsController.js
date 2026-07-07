// === semua model yang dipakai ===
require("dotenv").config();
const {
  QuizQuestionOptions,
  QuizQuestions,
  ActivityLogs,
  Users,
} = require("../models/index");

// =======================================================
// Get All Quiz Question Options
// =======================================================
exports.GetAllQuizQuestionOptions = async (req, res) => {
  try {
    // === find id user ===
    let { userid } = req.body;

    if (userid == null) {
      return res.status(400).json({ message: "ID Tidak Valid" });
    }

    let finduserbyid = await Users.findByPk(userid);

    if (finduserbyid == null) {
      return res
        .status(400)
        .json({ message: "User yang dicari tidak ketemu !!!" });
    }

    // === get all ===
    let alloptions = await QuizQuestionOptions.findAll();

    // === activity log ===
    await ActivityLogs.create({
      user_id: finduserbyid.id,
      activity: "Get All Quiz Question Options",
      ip_address: req.ip,
    });

    return res.status(200).json(alloptions);
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};

// =======================================================
// Get Quiz Question Option By ID
// =======================================================
exports.GetQuizQuestionOptionById = async (req, res) => {
  try {
    // === find id user ===
    let { userid, optionid } = req.body;

    if (userid == null) {
      return res.status(400).json({ message: "ID Tidak Valid" });
    }

    let finduserbyid = await Users.findByPk(userid);

    if (finduserbyid == null) {
      return res
        .status(400)
        .json({ message: "User yang dicari tidak ketemu !!!" });
    }

    // === cari data ===
    let option = await QuizQuestionOptions.findByPk(optionid);

    if (option == null) {
      return res.status(400).json({
        message: `Quiz Question Option dengan id ${optionid} tidak ditemukan`,
      });
    }

    // === activity log ===
    await ActivityLogs.create({
      user_id: finduserbyid.id,
      activity: "Get Quiz Question Option By ID",
      ip_address: req.ip,
    });

    return res.status(200).json(option);
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};

// =======================================================
// Insert
// =======================================================
exports.InsertQuizQuestionOption = async (req, res) => {
  try {
    // === tangkap data ===
    let { quiz_question_id, option_letter, option_text } = req.body;
    let user = req.user;

    // === cek quiz question ===
    let findquestion = await QuizQuestions.findByPk(quiz_question_id);

    if (findquestion == null) {
      return res.status(400).json({
        message: "Quiz Question Tidak Ditemukan",
      });
    }

    // === insert ===
    let createoption = await QuizQuestionOptions.create({
      quiz_question_id,
      option_letter,
      option_text,
    });

    // === activity log ===
    await ActivityLogs.create({
      user_id: user.id,
      activity: `Membuat Quiz Question Option Baru Dengan ID : ${createoption.id}`,
      ip_address: req.ip,
    });

    return res.status(200).json(createoption);
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};

// =======================================================
// Update
// =======================================================
exports.UpdateQuizQuestionOption = async (req, res) => {
  try {
    // === find user ===
    let user = req.user;
    let { id, option_letter, option_text } = req.body;

    // === cari data ===
    let carioption = await QuizQuestionOptions.findByPk(id);

    if (carioption == null) {
      return res.status(400).json({
        message: "Quiz Question Option Tidak Ketemu",
      });
    }

    // === update ===
    carioption.option_letter = option_letter;
    carioption.option_text = option_text;

    await carioption.save();

    // === activity log ===
    await ActivityLogs.create({
      user_id: user.id,
      activity: `Quiz Question Option dengan id : ${id} telah dirubah`,
      ip_address: req.ip,
    });

    return res.status(200).json(carioption);
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};

// =======================================================
// Delete
// =======================================================
exports.DeleteQuizQuestionOption = async (req, res) => {
  try {
    // === find user ===
    let user = req.user;
    let { id } = req.body;

    // === cari data ===
    let carioption = await QuizQuestionOptions.findByPk(id);

    if (carioption == null) {
      return res.status(400).json({
        message: "Quiz Question Option Tidak Ketemu",
      });
    }

    let temp = carioption;

    // === delete ===
    await carioption.destroy();

    // === activity log ===
    await ActivityLogs.create({
      user_id: user.id,
      activity: `Quiz Question Option dengan id ${id} telah didelete`,
      ip_address: req.ip,
    });

    return res.status(200).json(temp);
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};
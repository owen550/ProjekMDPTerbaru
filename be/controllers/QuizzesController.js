// === semua model yang dipakai ===
require("dotenv").config();
const {Quizzes,ActivityLogs,Users} = require("../models/index");
const bcrypt = require('bcrypt');

exports.GetAllQuizzesByTopicId = async (req, res) => { 
  try {
    // === find id user ===
    let { userid } = req.body
    if(userid == null){
      return res.status(400).json({message: "ID Tidak Valid"})
    }
    let finduserbyid = await Users.findByPk(userid);
    if(finduserbyid == null){
      return res.status(400).json({message: "User yang dicari tidak ketemu !!!"})
    }

    // === get all quizz === 
    let allquizz = await Quizzes.findAll()

    // === activity log ===
    await ActivityLogs.create({
      user_id: finduserbyid.id,
      activity: `Get All Quizzes`,
      ip_address: req.ip // cara dapetin ip clien
    })

    // === return ===
    return res.status(200).json(allquizz);
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};

exports.GetQuizzesById = async (req, res) => { 
  try {
    // === find id user ===
    let { userid, quizzid } = req.body 
    if(userid == null){
      return res.status(400).json({message: "ID Tidak Valid"})
    }
    let finduserbyid = await Users.findByPk(userid);
    if(finduserbyid == null){
      return res.status(400).json({message: "User yang dicari tidak ketemu !!!"})
    }

    // === get all by topic id === 
    let allquizz = await Quizzes.findByPk(quizzid)
    if(allquizz == null){
      return res.status(400).json({message: `quizz dengan id ${quizzid} tidak ditemukan`})
    }

    // === activity log ===    
    await ActivityLogs.create({
      user_id: finduserbyid.id,
      activity: "Get Quizz By ID",
      ip_address: req.ip // cara dapetin ip clien
    })

    // === return ===
    return res.status(200).json(allquizz);
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};

// topic_id  quiz_category  question_type

// insert (pastikan user adalah guru, pastikan user valid)
exports.InsertQuizzes = async (req, res) => {
  try {
    // === tangkap id user ===
    let { topic_id,quiz_category,question_type } = req.body
    let user = req.user

    // === insert course === !!! MAIN CODE ON FUNCTION
    let createquizz = await Quizzes.create({
        topic_id,quiz_category,question_type
    })

    // === activity log ===
    await ActivityLogs.create({
      user_id: user.id,
      activity: `Membuat Quizz Baru Dengan ID : ${createquizz.id}`,
      ip_address: req.ip // cara dapetin ip clien
    })

    // === return ===
    return res.status(200).json(createquizz);
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};

// update
exports.UpdateQuizzes = async (req, res) => {
  try {
    // === find id user ===
    let user = req.user
    let { id,quiz_category,question_type } = req.body

    // === cari quizz ===
    let cariquizz = await Quizzes.findByPk(id)
    if(cariquizz == null){
        return res.status(400).json({message: "Topic Course Tidak Ketemu"}) // cek g ketemu
    }

    // === ubah isinya dan save
    cariquizz.quiz_category = quiz_category
    cariquizz.question_type = question_type
    await cariquizz.save()

    // return
    return res.status(200).json(cariquizz);

    // === activity log ===
    await ActivityLogs.create({
      user_id: user.id,
      activity: `Quizz dengan id: ${id} telah dirubah`,
      ip_address: req.ip // cara dapetin ip clien
    })

    // === return ===
    return res.status(200).json(course);

  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};

// delete
exports.DeleteQuizzes = async (req, res) => {
  try {
    // === find id user ===
    let user = req.user
    let { id } = req.body

    // === cari quizz ===
    let cariquizz = await Quizzes.findByPk(id)
    if(cariquizz == null){
        return res.status(400).json({message: "Topic Course Tidak Ketemu"}) // cek g ketemu
    }
    temp = cariquizz
    
    // === delete quizz
    await cariquizz.destroy()

    // === activity log ===    
    await ActivityLogs.create({
      user_id: user.id,
      activity: `Quizz dengan id ${id} telah didelete`,
      ip_address: req.ip // cara dapetin ip clien
    })

    // === return ===
    return res.status(200).json(temp);
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};
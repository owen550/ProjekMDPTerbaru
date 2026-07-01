// === semua model yang dipakai ===
require("dotenv").config();
const {Courses,ActivityLogs,Users} = require("../models/index");
const bcrypt = require('bcrypt');

exports.GetAllCourse = async (req, res) => { 
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

    // === get all course === !!! MAIN CODE ON FUNCTION // <<< ON Process
    

    // === activity log ===    
    await ActivityLogs.create({
      user_id: finduserbyid.id,
      activity: "Get All User",
      ip_address: req.ip // cara dapetin ip clien
    })

    // === return ===
    return res.status(200).json(getAllDataUser);
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};

// insert
exports.InsertCourse = async (req, res) => {
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

    // === insert course === !!! MAIN CODE ON FUNCTION
    

    // === activity log ===    
    await ActivityLogs.create({
      user_id: finduserbyid.id,
      activity: "Get All User",
      ip_address: req.ip // cara dapetin ip clien
    })

    // === return ===
    return res.status(200).json(getAllDataUser);
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};

// update
exports.UpdateCourse = async (req, res) => {
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

    // === update course === !!! MAIN CODE ON FUNCTION
    

    // === activity log ===    
    await ActivityLogs.create({
      user_id: finduserbyid.id,
      activity: "Get All User",
      ip_address: req.ip // cara dapetin ip clien
    })

    // === return ===
    return res.status(200).json(getAllDataUser);
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};

// delete
exports.DeleteCourse = async (req, res) => {
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

    // === delete course === !!! MAIN CODE ON FUNCTION
    

    // === activity log ===    
    await ActivityLogs.create({
      user_id: finduserbyid.id,
      activity: "Get All User",
      ip_address: req.ip // cara dapetin ip clien
    })

    // === return ===
    return res.status(200).json(getAllDataUser);
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};
// enum catrgory : 
// ('mathematics', 'science', 'art', 'technology', 'social')

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
    let carikursus = await Courses.findAll()

    // === activity log ===    
    await ActivityLogs.create({
      user_id: finduserbyid.id,
      activity: "Get All User",
      ip_address: req.ip // cara dapetin ip clien
    })

    // === return ===
    return res.status(200).json(carikursus);
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};

exports.GetCourseById = async (req, res) => { 
  try {
    // === find id user ===
    let { userid, courseid } = req.body 
    if(userid == null){
      return res.status(400).json({message: "ID Tidak Valid"})
    }
    let finduserbyid = await Users.findByPk(userid);
    if(finduserbyid == null){
      return res.status(400).json({message: "User yang dicari tidak ketemu !!!"})
    }

    // === get all course === !!! MAIN CODE ON FUNCTION 
    let carikursus = await Courses.findByPk(courseid)
    if(carikursus == null){
      return res.status(400).json({message: "Kursus yang dicari tidak ketemu !!!"})
    }

    // === activity log ===    
    await ActivityLogs.create({
      user_id: finduserbyid.id,
      activity: "Get All User",
      ip_address: req.ip // cara dapetin ip clien
    })

    // === return ===
    return res.status(200).json(carikursus);
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};

// insert (pastikan user adalah guru, pastikan user valid)
exports.InsertCourse = async (req, res) => {
  try {
    // === tangkap id user ===
    let { title,category } = req.body
    let user = req.user

    // === cari course ===
    let carikursus = await Courses.findAll()
    if(carikursus.length > 0){ 
        // cek jangan sampai jduulnya ada yang sama
        carikursus.forEach(e => {
            if(e.title == title){
                return res.status(400).json({message: "Judul yang anda gunakan sudah dipakai di kursus lain"})
            }
        });
    }

    // === insert course === !!! MAIN CODE ON FUNCTION
    let createcourse = await Courses.create({
        title,
        category,
        teacher_id: user.id
    })

    // === activity log ===
    await ActivityLogs.create({
      user_id: user.id,
      activity: `Membuat Course Baru Berjudul ${title}`,
      ip_address: req.ip // cara dapetin ip clien
    })

    // === return ===
    return res.status(200).json(createcourse);
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};

// update
exports.UpdateCourse = async (req, res) => {
  try {
    // === find id user ===
    let user = req.user
    let course = req.course
    let {title,category} = req.body

    // === update course === !!! MAIN CODE ON FUNCTION
    let judullama = course.title
    course.title = title
    course.category = category
    await course.save()
    

    // === activity log ===    
    await ActivityLogs.create({
      user_id: user.id,
      activity: `Merubah Judul Dari ${judullama} menjadi ${course.title}`,
      ip_address: req.ip // cara dapetin ip clien
    })

    // === return ===
    return res.status(200).json(course);

  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};

// delete
exports.DeleteCourse = async (req, res) => {
  try {
    // === find id user ===
    let user = req.user

    // === delete course === !!! MAIN CODE ON FUNCTION
    let course = req.course
    await course.destroy()

    // === activity log ===    
    await ActivityLogs.create({
      user_id: user.id,
      activity: `Course ${course.title} telah didelete`,
      ip_address: req.ip // cara dapetin ip clien
    })

    // === return ===
    return res.status(200).json(course);
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};
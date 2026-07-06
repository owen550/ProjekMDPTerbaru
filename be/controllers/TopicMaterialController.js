// enum catrgory : 
// ('mathematics', 'science', 'art', 'technology', 'social')

// === semua model yang dipakai ===
require("dotenv").config();
const {TopicMaterials,ActivityLogs,Users} = require("../models/index");
const bcrypt = require('bcrypt');

exports.GetAllTopicMaterial = async (req, res) => { 
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
    let caritopicmaterial = await TopicMaterials.findAll()

    // === activity log ===
    await ActivityLogs.create({
      user_id: finduserbyid.id,
      activity: `Get All Topic Material`,
      ip_address: req.ip // cara dapetin ip clien
    })

    // === return ===
    return res.status(200).json(caritopicmaterial);
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};

exports.GetTopicMaterialById = async (req, res) => { 
  try {
    // === find id user ===
    let { userid, topic_id } = req.body 
    if(userid == null){
      return res.status(400).json({message: "ID Tidak Valid"})
    }
    let finduserbyid = await Users.findByPk(userid);
    if(finduserbyid == null){
      return res.status(400).json({message: "User yang dicari tidak ketemu !!!"})
    }

    // === get all course === !!! MAIN CODE ON FUNCTION 
    let caritopicmaterial = await TopicMaterials.findOne({where:{topic_id}})
    if(caritopicmaterial == null){
      return res.status(400).json({message: "Topic Materialid yang dicari tidak ketemu !!!"})
    }

    // === activity log ===    
    await ActivityLogs.create({
      user_id: finduserbyid.id,
      activity: "Get Topic Material By ID",
      ip_address: req.ip // cara dapetin ip clien
    })

    // === return ===
    return res.status(200).json(caritopicmaterial);
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};

// insert (pastikan user adalah guru, pastikan user valid)
exports.InsertTopicCourseTopic = async (req, res) => {
  try {
    // === tangkap id user ===
    let { topic_id,video_url,attachment_file } = req.body
    let user = req.user

    // === cari sek lek ada ===
    let cekwesada = await TopicMaterials.findOne({where:{topic_id}})
    if(cekwesada != null){
      return res.status(400).json({message: "Topic Course Sudah Ada, Silahkan update !!!"})
    }

    // === cari course ===
    let caritopicmaterial = await TopicMaterials.findAll()
    if(caritopicmaterial.length < 0){
        // cek jangan sampai jduulnya ada yang sama
        return res.status(400).json({message: "Topic Course Tidak Ketemu"})
    }

    // === insert course === !!! MAIN CODE ON FUNCTION
    let createcourse = await TopicMaterials.create({
        topic_id,
        video_url,
        attachment_file
    })

    // === activity log ===
    await ActivityLogs.create({
      user_id: user.id,
      activity: `Membuat Topic Material Baru : ${attachment_file}`,
      ip_address: req.ip // cara dapetin ip clien
    })

    // === return ===
    return res.status(200).json(createcourse);
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};

// update
exports.UpdateTopicMaterial = async (req, res) => {
  try {
    // === find id user ===
    let user = req.user

    let {topic_id,video_url,attachment_file} = req.body

    // === update course === !!! MAIN CODE ON FUNCTION
    let cektopicmaterial = await TopicMaterials.findOne({
      where:{topic_id}
    })
    if(cektopicmaterial == null){
      return res.status(400).json({ message: 'TopcMaterial yang dicari ngak ketemu' });
    }
    cektopicmaterial.video_url = video_url
    cektopicmaterial.attachment_file = attachment_file
    cektopicmaterial.save()

    // return
    return res.status(200).json(cektopicmaterial);

    // === activity log ===
    await ActivityLogs.create({
      user_id: user.id,
      activity: `Topic Material dengan topic_id: ${topic_id} telah dirubah`,
      ip_address: req.ip // cara dapetin ip clien
    })

    // === return ===
    return res.status(200).json(course);

  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};

// delete
exports.DeleteTopicMaterial = async (req, res) => {
  try {
    // === find id user ===
    let user = req.user

    // === delete course === !!! MAIN CODE ON FUNCTION
    let {topic_id} = req.body
    if(topic_id == null){
      return res.status(400).json({ message: 'topic_id tidak valid' });
    }
    // find topicmat
    let findtopicmat = await TopicMaterials.findOne({where:{topic_id}})
    if(findtopicmat == null){
      return res.status(400).json({ message: 'TopicMaterial yang dicari ngak ketemu' });
    }

    // delete topic material
    let temp = findtopicmat
    await findtopicmat.destroy()

    // === activity log ===    
    await ActivityLogs.create({
      user_id: user.id,
      activity: `Course ${course.title} telah didelete`,
      ip_address: req.ip // cara dapetin ip clien
    })

    // === return ===
    return res.status(200).json(temp);
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};
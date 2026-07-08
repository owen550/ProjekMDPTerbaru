// === semua model yang dipakai ===
require("dotenv").config();
const {Users,ActivityLogs} = require("../models/index");
const bcrypt = require('bcrypt');

exports.GetAllUser = async (req, res) => {
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

    // === get all user ===
    let getAllDataUser = await Users.findAll();

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

exports.AddUser = async (req, res) => {
  try { // ntik lewatin middleware dulu jangan lupa !!!
    const { name,username,password_user,email,google_id,birthday_date,role,tier } = req.body; // ntik dirubah
   
    // === proses password ===
    let passwordnow = password_user // password user sekarang
    let saltpassword = parseInt(process.env.SALTROUNDS) // panjang bcrypt
    const password = await bcrypt.hash(passwordnow,saltpassword) // enkripsinya
    
    // === cek jika ada nama user yang sama ? tolak 
    let allUserData = await Users.findOne({where: {username}})
    let allUserDataEmail = await Users.findOne({where: {username}})
    if(allUserData != null || allUserDataEmail != null){
      return res.status(400).json({
        message: "Username Atau Email Telah Dipakai !!!",
      });
    }

    // === inputkan ke db jika lolos ===
    let newUser = await Users.create({name,username,password,email,google_id,birthday_date,role,tier});

    // === activity log ===    
    await ActivityLogs.create({
      user_id: newUser.id,
      activity: "Mendaftar Sebagai User Baru",
      ip_address: req.ip // cara dapetin ip clien
    })
    
    return res.status(200).json({
      message: "Berhasil Add User",
      status: '201',
      newUser
    });
  } catch (error) {
    return res.status(500).json({ 
      message: error.message,
    });
  }
};

exports.GetUserById = async (req, res) => {
  try {
    let { userid, userpencari } = req.body

    // cari userpencari 
    if(userpencari == null){
      return res.status(400).json({message: "ID Tidak Valid pencari"})
    }
    let finduserpencari = await Users.findByPk(userpencari);
    if(finduserpencari == null){
      return res.status(400).json({message: "User pencari tidak ketemu !!!"})
    }

    // cari user target
    if(userid == null){
      return res.status(400).json({message: "ID Tidak Valid dicari"})
    }
    let finduserbyid = await Users.findByPk(userid);
    if(finduserbyid == null){
      return res.status(400).json({message: "User yang dicari tidak ketemu !!!"})
    }

    // === activity log ===    
    await ActivityLogs.create({
      user_id: userpencari,
      activity: "Get Seorang User",
      ip_address: req.ip // cara dapetin ip clien
    })

    return res.status(200).json(finduserbyid);
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};

exports.UpdateUserById = async (req, res) => {
  try {
    const {
      userid,
      name,
      username,
      email,
      role,
      tier
    } = req.body;
    const user = await Users.findByPk(userid);
    if (!user) {
      return res.status(404).json({ message: "User tidak ditemukan" });
    }

    user.name = name;
    user.username = username;
    user.email = email;
    user.role = role;
    user.tier = tier;
    await user.save();

    // === activity log ===    
    await ActivityLogs.create({
      user_id: user.id,
      activity: "User Melakukan Update Data Diri",
      ip_address: req.ip // cara dapetin ip clien
    })

    return res.status(200).json(user);
  }
  catch (error) {
    return res.status(500).json(error);
  }
};

exports.DeleteUser = async (req, res) => {
  try {
    let { userid } = req.body 
    if(userid == null){
      return res.status(400).json({message: "ID Tidak Valid"})
    }
    let finduserbyid = await Users.findByPk(userid);
    if(finduserbyid == null){
      return res.status(400).json({message: "User yang dicari tidak ketemu !!!"})
    }

    // === activity log ===    
    await ActivityLogs.create({
      user_id: finduserbyid.id,
      activity: "User Didelete",
      ip_address: req.ip // cara dapetin ip clien
    })

    // kalau ketemu maka delete
    await finduserbyid.destroy()
    return res.status(200).json({
      message: "Berhasil Delete User",
      data: finduserbyid
    });
  } catch (error) {
    return res.status(500).json(error);
  }
};

exports.LoginUser = async (req, res) => {
  try {
    let { usernameoremail,password } = req.body 
    if(usernameoremail == null || password == ""){
      return res.status(400).json({message: "Usermname/Email/Password Tidak Valid"})
    }

    let userdata = false

    // cek username
    let cariuserbyusername = await Users.findOne({
      where:{
        username: usernameoremail,
      }
    })
    // simpan user
    if(cariuserbyusername != null){
      userdata = cariuserbyusername
    }
    let cariuserbyemail= await Users.findOne({
      where:{
        email: usernameoremail,
      }
    })
    // simpan user
    if(cariuserbyemail != null){
      userdata = cariuserbyemail
    }
    // cek kalau keduanya kosong maka g ketemu
    if(userdata == null){
      return res.status(400).json({
        message: "Login Gagal"
      })
    }
    // berarti ketemu, lakukan bcry
    let passcomp = await bcrypt.compare(password,userdata.password)
    if(!passcomp){ // berarti gagal login
      return res.status(400).json({
        message: "Login Gagal"
      })
    }

    // === activity log ===
    await ActivityLogs.create({
      user_id: userdata.id,
      activity: `User dengan id ${userdata.id} Login`,
      ip_address: req.ip // cara dapetin ip clien
    })

    // klo sampek sini berarti berhasil
    return res.status(200).json(userdata)
    
  } catch (error) {
    return res.status(500).json(error);
  }
};

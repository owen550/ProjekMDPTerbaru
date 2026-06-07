const jwt = require("jsonwebtoken");
require("dotenv").config();

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

CekKelengkapanDataAddUser = async (req, res, next) => {
  try {
    const { name,username,password_user,email,google_id,birthday_date,role,tier } = req.body; // ntik dirubah
    if(!name || !email || !role ){
        return res.status(400).json({
          message: 'Kredensial Tidak Lengkap !!!',
          status: 400
        })
    }
    next();
  } catch (err) {
    return res.status(500).send({
      message: err.message,
      status: 500
    });
  }
};

module.exports = {
  CekKelengkapanDataAddUser
}

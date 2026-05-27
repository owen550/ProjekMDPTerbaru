// === semua model yang dipakai ===
// const BahanBaku = require("../models/bahanBakuModel");


// === semua api ===
// exports.getAllBahanBaku = async (req, res) => {
//   try {
//     const bahanBakuList = await BahanBaku.findAll();
//     return res.status(200).json(bahanBakuList);
//   } catch (error) {
//     return res.status(500).json({ error: error.message });
//   }
// };

const data = [
    {
        id: 1,
        nama: 'kumar'
    },
    {
        id: 2,
        nama: 'asep'
    },
    {
        id: 3,
        nama: 'yanto'
    },
]

exports.tesapi = async (req, res) => {
  try {
    res.status(200).json(data);
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};
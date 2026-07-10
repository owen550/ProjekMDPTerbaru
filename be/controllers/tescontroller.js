// === semua model yang dipakai ===
// const BahanBaku = require("../models/bahanBakuModel");
const { GoogleGenAI } = require("@google/genai");
require("dotenv").config();
const ai = new GoogleGenAI({});

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
    nama: "kumar",
  },
  {
    id: 2,
    nama: "asep",
  },
  {
    id: 3,
    nama: "yanto",
  },
];
var user = {
  username: null,
  aicontent: [],
};

exports.tesapi = async (req, res) => {
  try {
    res.status(200).json(data);
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};

var list_user = []; // simpamn percakapan user
// const chat = [
//   {role:'model',parts:[{text: 'tes halo, saya suka pria solo'}]}
// ]// ini buat simpan history chat, ntik pindah ke db

exports.tesai = async (req, res) => {
  try {
    // === tangkap isi body ===
    const { role, pesan } = req.body;

    // === validasi ===
    if (!pesan || !role) {
      //
      return res.status(400).json({
        success: false,
        message: "Pesan Anda Tidak Valid atau Role Tak Valid",
      });
    }

    // === kalau user belum ada maka add user ===
    if (list_user.find((it) => it.username == role) == null) {
      // tidak ada maka buat baru
      var user_new = { ...user };
      user_new.username = role;
      user_new.aicontent = [
        {
          role: "user",
          parts: [{ text: pesan }],
        },
      ];
      list_user.push(user_new);
    } else {
      // ada, cari obj nya
      for (var i = 0; i < list_user.length; i++) {
        if (list_user[i].username == role) {
          list_user[i].aicontent.push({
            role: "user",
            parts: [{ text: pesan }],
          });
        }
      }
    }

    // === filter chat user ===
    var filterchat = list_user.find((it) => it.username == role);

    // === mulai api ke gemini ===
    const response = await ai.models.generateContent({
      model: "gemini-3.5-flash", // gemini-2.5-flash gemini-3.5-flash
      systemInstruction: `
      Anda adalah customer service profesional.

      ATURAN WAJIB:

      - Jangan pernah mengatakan:
        "Sebagai AI"
        "Saya tidak memiliki memori"
        "Saya tidak dapat mengingat"
        "Saya tidak memiliki ingatan jangka panjang"

      - Jangan membahas keterbatasan AI.
      - Jangan memberi disclaimer teknis.
      - Jawab seperti manusia profesional.

      - Jika informasi tersedia di history:
        jawab dengan yakin dan langsung.

      - Jika informasi TIDAK tersedia:
        jawab secara profesional seperti customer service.

      CONTOH:

      User: apa kode tebak saya?
      Jawaban benar:
      "Kode tebak Anda adalah 123#123"

      Jika data tidak ada:
      "Saya tidak memiliki informasi mengenai kode tebak yang Anda maksud."

      Jawaban yang DILARANG:
      "Sebagai AI..."
      "Saya tidak memiliki memori..."
      "Saya tidak dapat mengingat..."
      `,
      contents: filterchat.aicontent,
    });

    // === respon gemini ===
    return res.status(200).json({
      success: true,
      message: "AI Berhasil Merespon",
      data: response.text,
    });
  } catch (error) {
    return res.status(500).json({ error: error.message });
  }
};

// === ||| Bagian Ini Wajib Ada ||| ===
require('dotenv').config();
const express = require("express");
const cors = require("cors");
const bodyParser = require("body-parser");
const { sequelize } = require("./config/sequelize");
const app = express();
app.use(cors());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true })); // ini biar bisa terima body !!!

app.set("trust proxy", true); // buat dapetin ip addrest

// === ||| Semua Routes ||| ===

const TesRoutes = require("../be/routes/tesroutes");
const UsersRoutes = require("../be/routes/UsersRoutes");
const CourseRoutes = require("../be/routes/CourseRoutes");
const CourseTopicRoutes = require("../be/routes/CourseTopicRoutes");
const TopicMaterialRoutes = require("../be/routes/TopicMaterialRoutes");
const AdminMessagesRoutes = require("../be/routes/AdminMessagesRoutes");
const QuizzesRoutes = require("../be/routes/QuizzesRoutes");
const QuizQuestionsRoutes = require("../be/routes/QuizQuestionsRoutes");
const CsChatbotChatsRoutes = require("../be/routes/CsChatbotChatsRoutes");
const PaymentsRoutes = require("../be/routes/PaymentsRoutes");
const CourseEnrollmentsRoutes = require("../be/routes/CourseEnrollmentsRoutes");
const QuizQuestionOptionsRoutes = require("../be/routes/QuizQuestionOptionsRoutes");
const StudentSubmissionsRoutes = require("../be/routes/StudentSubmissionsRoutes");
const ActivityLogRoutes = require("../be/routes/ActivityLogRoutes");

// === ||| Tes API ||| ===
const port = 3000;
app.listen(port, () => {
  console.log(`Server berjalan di http://localhost:${port}`);
});
app.get("/", async (req, res) => {
  res.status(200).send("Pesan Terkirim");
});

// === ||| SEMUA YANG AKAN DIPANGGIL DI API ||| ===
app.use("/api/test", TesRoutes);
app.use("/api/users", UsersRoutes);
app.use("/api/course", CourseRoutes);
app.use("/api/coursetopic", CourseTopicRoutes);
app.use("/api/topicmaterial", TopicMaterialRoutes);
app.use("/api/adminmessages", AdminMessagesRoutes);
app.use("/api/quizz", QuizzesRoutes);
app.use("/api/quizquestions", QuizQuestionsRoutes);
app.use("/api/chatbot", CsChatbotChatsRoutes);
app.use("/api/payments", PaymentsRoutes);
app.use("/api/courseenrollments", CourseEnrollmentsRoutes);
app.use("/api/quizquestionoptions", QuizQuestionOptionsRoutes);
app.use("/api/studentsubmissionsroutes", StudentSubmissionsRoutes);
app.use("/api/activitylogroutes", ActivityLogRoutes);

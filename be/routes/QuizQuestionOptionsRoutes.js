const express = require("express");
const router = express.Router();
const CourseUser = require("../middleware/UsersMiddleware");
const QuizQuestionOptionsController = require("../controllers/QuizQuestionOptionsController");

// === ||| semua routes ||| ====

// Get All
router.post(
    "/alldata",
    CourseUser.CekUserIDTakKosong,
    CourseUser.CekUserItuAda,
    CourseUser.CekUserAdalahGuru,
    QuizQuestionOptionsController.GetAllQuizQuestionOptions
);

// Get By ID
router.post(
    "/getbyid",
    CourseUser.CekUserIDTakKosong,
    CourseUser.CekUserItuAda,
    CourseUser.CekUserAdalahGuru,
    QuizQuestionOptionsController.GetQuizQuestionOptionById
);

// Insert
router.post(
    "/insert",
    CourseUser.CekUserIDTakKosong,
    CourseUser.CekUserItuAda,
    CourseUser.CekUserAdalahGuru,
    QuizQuestionOptionsController.InsertQuizQuestionOption
);

// Update
router.put(
    "/update",
    CourseUser.CekUserIDTakKosong,
    CourseUser.CekUserItuAda,
    CourseUser.CekUserAdalahGuru,
    QuizQuestionOptionsController.UpdateQuizQuestionOption
);

// Delete
router.put(
    "/delete",
    CourseUser.CekUserIDTakKosong,
    CourseUser.CekUserItuAda,
    CourseUser.CekUserAdalahGuru,
    QuizQuestionOptionsController.DeleteQuizQuestionOption
);

// === ||| Export ||| ===
module.exports = router;
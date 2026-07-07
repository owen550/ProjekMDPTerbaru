const express = require("express");
const router = express.Router();

const CourseUser = require("../middleware/UsersMiddleware");
const CourseMiddleware = require("../middleware/CourseMiddleware");
const CourseTopicMiddleware = require("../middleware/CourseTopicMiddleware");

const QuizQuestionsController = require("../controllers/QuizQuestionsController");

// === ||| semua routes ||| ====

// Get All
router.get(
    "/alldata",
    CourseUser.CekUserIDTakKosong,
    CourseUser.CekUserItuAda,
    CourseUser.CekUserAdalahGuru,
    QuizQuestionsController.GetAllQuizQuestions
);

// Get By ID
router.get(
    "/getbyid",
    CourseUser.CekUserIDTakKosong,
    CourseUser.CekUserItuAda,
    CourseUser.CekUserAdalahGuru,
    QuizQuestionsController.GetQuizQuestionById
);

// Insert
router.post(
    "/insert",
    CourseUser.CekUserIDTakKosong,
    CourseUser.CekUserItuAda,
    CourseUser.CekUserAdalahGuru,
    QuizQuestionsController.InsertQuizQuestion
);

// Update
router.put(
    "/update",
    CourseUser.CekUserIDTakKosong,
    CourseUser.CekUserItuAda,
    CourseUser.CekUserAdalahGuru,
    QuizQuestionsController.UpdateQuizQuestion
);

// Delete
router.put(
    "/delete",
    CourseUser.CekUserIDTakKosong,
    CourseUser.CekUserItuAda,
    CourseUser.CekUserAdalahGuru,
    QuizQuestionsController.DeleteQuizQuestion
);

// === ||| Export ||| ===
module.exports = router;
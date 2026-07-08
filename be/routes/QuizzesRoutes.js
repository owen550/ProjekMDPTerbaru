const express = require("express");
const router = express.Router();

const CourseUser = require("../middleware/UsersMiddleware")
const CourseMiddleware = require("../middleware/CourseMiddleware")
const CourseTopicMiddleware = require("../middleware/CourseTopicMiddleware")

const QuizzesController = require("../controllers/QuizzesController");

// === ||| semua routes ||| ====
router.post( // get all
    "/alldata",
    CourseUser.CekUserIDTakKosong,
    CourseUser.CekUserItuAda,
    CourseUser.CekUserAdalahGuru,
    QuizzesController.GetAllQuizzesByTopicId
);
router.post( // get by id
    "/getbyid",
    CourseUser.CekUserIDTakKosong,
    CourseUser.CekUserItuAda,
    CourseUser.CekUserAdalahGuru,
    QuizzesController.GetQuizzesById
);

router.post( // update
    "/insert",
    CourseUser.CekUserIDTakKosong,
    CourseUser.CekUserItuAda,
    CourseUser.CekUserAdalahGuru,
    QuizzesController.InsertQuizzes
);

router.put( // update
    "/update",
    CourseUser.CekUserIDTakKosong,
    CourseUser.CekUserItuAda,
    CourseUser.CekUserAdalahGuru,
    QuizzesController.UpdateQuizzes
);

router.put( // update
    "/delete",
    CourseUser.CekUserIDTakKosong,
    CourseUser.CekUserItuAda,
    CourseUser.CekUserAdalahGuru,
    QuizzesController.DeleteQuizzes
);
// === ||| Export ||| ===
module.exports = router;
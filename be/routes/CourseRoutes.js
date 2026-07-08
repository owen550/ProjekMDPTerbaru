const express = require("express");
const router = express.Router();

const CourseUser = require("../middleware/UsersMiddleware")
const CourseMiddleware = require("../middleware/CourseMiddleware")
const CourseController = require("../controllers/CourseController");

// === ||| semua routes ||| ====
router.post( 
    "/alldata",
    CourseController.GetAllCourse
);
router.post( // get by id
    "/getbyid",
    CourseController.GetCourseById
);
router.post( // insert
    "/insert",
    CourseUser.CekUserIDTakKosong,
    CourseUser.CekUserItuAda,
    CourseUser.CekUserAdalahGuru,
    CourseController.InsertCourse
);
router.put( // update
    "/update",
    CourseUser.CekUserIDTakKosong,
    CourseUser.CekUserItuAda,
    CourseUser.CekUserAdalahGuru,
    CourseMiddleware.CariCourseByID,
    CourseController.UpdateCourse
);
router.put( // update
    "/delete",
    CourseUser.CekUserIDTakKosong,
    CourseUser.CekUserItuAda,
    CourseUser.CekUserAdalahGuru,
    CourseMiddleware.CariCourseByID,
    CourseController.DeleteCourse
);

// === ||| Export ||| ===
module.exports = router;
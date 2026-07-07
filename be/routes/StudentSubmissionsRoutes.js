const express = require("express");
const router = express.Router();

const CourseUser = require("../middleware/UsersMiddleware");

const StudentSubmissionsController = require("../controllers/StudentSubmissionsController");

// === ||| semua routes ||| ====

// Get All
router.get(
    "/alldata",
    CourseUser.CekUserIDTakKosong,
    CourseUser.CekUserItuAda,
    CourseUser.CekUserAdalahGuru,
    StudentSubmissionsController.GetAllStudentSubmissions
);

// Get By ID
router.get(
    "/getbyid",
    CourseUser.CekUserIDTakKosong,
    CourseUser.CekUserItuAda,
    CourseUser.CekUserAdalahGuru,
    StudentSubmissionsController.GetStudentSubmissionById
);

// Insert
router.post(
    "/insert",
    CourseUser.CekUserIDTakKosong,
    CourseUser.CekUserItuAda,
    CourseUser.CekUserAdalahGuru,
    StudentSubmissionsController.InsertStudentSubmission
);

// Update
router.put(
    "/update",
    CourseUser.CekUserIDTakKosong,
    CourseUser.CekUserItuAda,
    CourseUser.CekUserAdalahGuru,
    StudentSubmissionsController.UpdateStudentSubmission
);

// Delete
router.put(
    "/delete",
    CourseUser.CekUserIDTakKosong,
    CourseUser.CekUserItuAda,
    CourseUser.CekUserAdalahGuru,
    StudentSubmissionsController.DeleteStudentSubmission
);

// === ||| Export ||| ===
module.exports = router;
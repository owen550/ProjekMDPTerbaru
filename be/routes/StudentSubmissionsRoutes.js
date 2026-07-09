const express = require("express");
const router = express.Router();

const CourseUser = require("../middleware/UsersMiddleware");

const StudentSubmissionsController = require("../controllers/StudentSubmissionsController");

// === ||| semua routes ||| ====

// Get All
router.post(
    "/alldata",
    CourseUser.CekUserIDTakKosong,
    CourseUser.CekUserItuAda,
    StudentSubmissionsController.GetAllStudentSubmissions
);

// Get By ID
router.post(
    "/getbyid",
    CourseUser.CekUserIDTakKosong,
    CourseUser.CekUserItuAda,
    StudentSubmissionsController.GetStudentSubmissionById
);

// Insert
router.post(
    "/insert",
    CourseUser.CekUserIDTakKosong,
    CourseUser.CekUserItuAda,
    StudentSubmissionsController.InsertStudentSubmission
);

// Update
router.put(
    "/update",
    CourseUser.CekUserIDTakKosong,
    CourseUser.CekUserItuAda,
    StudentSubmissionsController.UpdateStudentSubmission
);

// Delete
router.put(
    "/delete",
    CourseUser.CekUserIDTakKosong,
    CourseUser.CekUserItuAda,
    StudentSubmissionsController.DeleteStudentSubmission
);

// === ||| Export ||| ===
module.exports = router;
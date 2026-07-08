const express = require("express");
const router = express.Router();

const CourseUser = require("../middleware/UsersMiddleware")
const CourseMiddleware = require("../middleware/CourseMiddleware")
const CourseTopicMiddleware = require("../middleware/CourseTopicMiddleware")

const CourseTopicController = require("../controllers/CourseTopicController");

// === ||| semua routes ||| ====
router.post( // update
    "/alldata",
    CourseUser.CekUserIDTakKosong,
    CourseUser.CekUserItuAda,
    CourseUser.CekUserAdalahGuru,
    CourseMiddleware.CariCourseByID,
    CourseTopicController.GetAllCourseTopicByID
);

router.post( // update
    "/insert",
    CourseUser.CekUserIDTakKosong,
    CourseUser.CekUserItuAda,
    CourseUser.CekUserAdalahGuru,
    CourseMiddleware.CariCourseByID,
    CourseTopicMiddleware.CekAttribut,
    CourseTopicController.InsertCourseTopic
);

router.put( // update
    "/update",
    CourseUser.CekUserIDTakKosong,
    CourseUser.CekUserItuAda,
    CourseUser.CekUserAdalahGuru,
    CourseTopicMiddleware.CourseTopicFindByID,
    CourseTopicController.UpdateCourseTopic
);

router.put( // update
    "/delete",
    CourseUser.CekUserIDTakKosong,
    CourseUser.CekUserItuAda,
    CourseUser.CekUserAdalahGuru,
    CourseTopicMiddleware.CourseTopicFindByID,
    CourseTopicController.DeleteCourseTopic
);
// === ||| Export ||| ===
module.exports = router;
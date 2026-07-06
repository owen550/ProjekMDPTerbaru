const express = require("express");
const router = express.Router();

const CourseUser = require("../middleware/UsersMiddleware")
const CourseMiddleware = require("../middleware/CourseMiddleware")
const CourseTopicMiddleware = require("../middleware/CourseTopicMiddleware")

const TopicMaterialController = require("../controllers/TopicMaterialController");

// === ||| semua routes ||| ====
router.get( // update
    "/alldata",
    CourseUser.CekUserIDTakKosong, // userid
    CourseUser.CekUserItuAda,
    CourseUser.CekUserAdalahGuru,
    TopicMaterialController.GetAllTopicMaterial
);

router.get( // update
    "/getbyid",
    CourseUser.CekUserIDTakKosong, // userid
    CourseUser.CekUserItuAda,
    CourseUser.CekUserAdalahGuru,
    TopicMaterialController.GetTopicMaterialById
);

router.post( // update
    "/insert",
    CourseUser.CekUserIDTakKosong, // userid
    CourseUser.CekUserItuAda,
    CourseUser.CekUserAdalahGuru,
    TopicMaterialController.InsertTopicCourseTopic
);

router.put( // update
    "/update",
    CourseUser.CekUserIDTakKosong,
    CourseUser.CekUserItuAda,
    CourseUser.CekUserAdalahGuru,
    TopicMaterialController.UpdateTopicMaterial
);

router.put( // delete
    "/delete",
    CourseUser.CekUserIDTakKosong,
    CourseUser.CekUserItuAda,
    CourseUser.CekUserAdalahGuru,
    TopicMaterialController.DeleteTopicMaterial
);
// === ||| Export ||| ===
module.exports = router;
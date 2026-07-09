const express = require("express");
const router = express.Router();

const CourseEnrollmentsController = require("../controllers/CourseEnrollmentsController");
const CourseEnrollmentsMiddleware = require("../middleware/CourseEnrollmentsMiddleware");

router.post(
  "/:userId/:studentId",
  CourseEnrollmentsMiddleware.validateEnrollmentInput,
  CourseEnrollmentsController.createEnrollment,
);

router.get(
  "/all/:userId",
  CourseEnrollmentsMiddleware.validateEnrollmentuserId,
  CourseEnrollmentsController.getAllEnrollments,
);

router.get(
  "/:userId/:studentId",
  CourseEnrollmentsMiddleware.validateEnrollmentAccess,
  CourseEnrollmentsController.getEnrollmentsByStudent,
);

router.get(
  "/:userId/:studentId/:enrollmentId",
  CourseEnrollmentsMiddleware.validateEnrollmentAccess,
  CourseEnrollmentsController.getEnrollmentById,
);

router.put(
  "/:userId/:studentId/:enrollmentId",
  CourseEnrollmentsMiddleware.validateEnrollmentInput,
  CourseEnrollmentsController.updateEnrollment,
);

router.delete(
  "/:userId/:studentId/:enrollmentId",
  CourseEnrollmentsMiddleware.validateEnrollmentAccess,
  CourseEnrollmentsController.deleteEnrollment,
);

module.exports = router;

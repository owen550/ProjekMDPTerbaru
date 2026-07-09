const { Users, Courses } = require("../models/index");

const validateEnrollmentInput = async (req, res, next) => {
  try {
    const userId = req.params.userId || req.body.user_id || req.query.user_id;
    const studentId =
      req.params.studentId || req.body.student_id || req.query.student_id;
    const courseId = req.body.course_id || req.query.course_id;
    const { student_id, course_id } = req.body;

    if (!userId) {
      return res.status(400).json({
        status: "error",
        message: "user_id is required",
      });
    }

    const resolvedStudentId = studentId || student_id;
    const resolvedCourseId = courseId || course_id;

    if (!resolvedStudentId || !resolvedCourseId) {
      return res.status(400).json({
        status: "error",
        message: "student_id and course_id are required",
      });
    }

    const user = await Users.findByPk(Number(userId));
    if (!user) {
      return res.status(404).json({
        status: "error",
        message: `User dengan id ${userId} tidak ditemukan`,
      });
    }

    const student = await Users.findByPk(Number(resolvedStudentId));
    if (!student) {
      return res.status(404).json({
        status: "error",
        message: `Student dengan id ${resolvedStudentId} tidak ditemukan`,
      });
    }

    const course = await Courses.findByPk(Number(resolvedCourseId));
    if (!course) {
      return res.status(404).json({
        status: "error",
        message: `Course dengan id ${resolvedCourseId} tidak ditemukan`,
      });
    }

    req.enrollmentUserId = Number(userId);
    next();
  } catch (error) {
    console.error("CourseEnrollmentsMiddleware.validateEnrollmentInput", error);
    return res.status(500).json({
      status: "error",
      message: "Failed to validate enrollment input",
      details: error.message,
    });
  }
};
const validateEnrollmentUser = async (req, res, next) => {
  try {
    const userId = req.params.userId || req.body.user_id || req.query.user_id;

    if (!userId) {
      return res.status(400).json({
        status: "error",
        message: "user_id is required",
      });
    }

    const user = await Users.findByPk(Number(userId));
    if (!user) {
      return res.status(404).json({
        status: "error",
        message: `User dengan id ${userId} tidak ditemukan`,
      });
    }

    req.enrollmentUserId = Number(userId);
    next();
  } catch (error) {
    console.error("CourseEnrollmentsMiddleware.validateEnrollmentUser", error);
    return res.status(500).json({
      status: "error",
      message: "Failed to validate enrollment access",
      details: error.message,
    });
  }
};
const validateEnrollmentAccess = async (req, res, next) => {
  try {
    const userId = req.params.userId || req.body.user_id || req.query.user_id;
    const studentId =
      req.params.studentId || req.body.student_id || req.query.student_id;

    if (!userId) {
      return res.status(400).json({
        status: "error",
        message: "user_id is required",
      });
    }

    if (!studentId) {
      return res.status(400).json({
        status: "error",
        message: "student_id is required",
      });
    }

    const user = await Users.findByPk(Number(userId));
    if (!user) {
      return res.status(404).json({
        status: "error",
        message: `User dengan id ${userId} tidak ditemukan`,
      });
    }

    const student = await Users.findByPk(Number(studentId));
    if (!student) {
      return res.status(404).json({
        status: "error",
        message: `Student dengan id ${studentId} tidak ditemukan`,
      });
    }

    req.enrollmentUserId = Number(userId);
    next();
  } catch (error) {
    console.error(
      "CourseEnrollmentsMiddleware.validateEnrollmentAccess",
      error,
    );
    return res.status(500).json({
      status: "error",
      message: "Failed to validate enrollment access",
      details: error.message,
    });
  }
};

module.exports = {
  validateEnrollmentInput,
  validateEnrollmentAccess,
  validateEnrollmentuserId: validateEnrollmentUser,
};

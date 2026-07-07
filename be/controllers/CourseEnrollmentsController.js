const {
  CourseEnrollments,
  Users,
  Courses,
  ActivityLogs,
} = require("../models/index");

const createEnrollment = async (req, res) => {
  try {
    const { student_id, course_id, is_bookmarked, status } = req.body;
    const userId = req.enrollmentUserId;

    if (!student_id || !course_id || !userId) {
      return res.status(400).json({
        status: "error",
        message: "student_id, course_id, and user_id are required",
      });
    }

    const student = await Users.findByPk(Number(student_id));
    if (!student) {
      return res.status(404).json({
        status: "error",
        message: "Student tidak ditemukan",
      });
    }

    const course = await Courses.findByPk(Number(course_id));
    if (!course) {
      return res.status(404).json({
        status: "error",
        message: "Course tidak ditemukan",
      });
    }

    const existingEnrollment = await CourseEnrollments.findOne({
      where: {
        student_id: Number(student_id),
        course_id: Number(course_id),
      },
    });

    if (existingEnrollment) {
      return res.status(409).json({
        status: "error",
        message: "User sudah terdaftar pada course ini",
      });
    }

    const newEnrollment = await CourseEnrollments.create({
      student_id: Number(student_id),
      course_id: Number(course_id),
      is_bookmarked: is_bookmarked ?? false,
      status: status || "active",
    });

    await ActivityLogs.create({
      user_id: Number(userId),
      activity: `User id ${userId} mendaftarkan student ${student_id} ke course ${course_id}`,
      ip_address: req.ip,
    });

    return res.status(201).json({
      status: "success",
      data: newEnrollment,
    });
  } catch (error) {
    console.error("CourseEnrollmentsController.createEnrollment", error);
    return res.status(500).json({
      status: "error",
      message: "Failed to create enrollment",
      details: error.message,
    });
  }
};

const getEnrollmentsByStudent = async (req, res) => {
  try {
    const studentId = req.params.studentId || req.params.student_id;
    const userId = req.enrollmentUserId;

    if (!studentId || !userId) {
      return res.status(400).json({
        status: "error",
        message: "student_id and user_id are required",
      });
    }

    const enrollments = await CourseEnrollments.findAll({
      where: {
        student_id: Number(studentId),
      },
      order: [["created_at", "DESC"]],
    });

    return res.status(200).json({
      status: "success",
      data: enrollments,
    });
  } catch (error) {
    console.error("CourseEnrollmentsController.getEnrollmentsByStudent", error);
    return res.status(500).json({
      status: "error",
      message: "Failed to get enrollments",
      details: error.message,
    });
  }
};

const getAllEnrollments = async (req, res) => {
  try {
    const userId = req.enrollmentUserId;

    if (!userId) {
      return res.status(400).json({
        status: "error",
        message: "user_id is required",
      });
    }

    const enrollments = await CourseEnrollments.findAll({
      order: [["created_at", "DESC"]],
    });

    await ActivityLogs.create({
      user_id: Number(userId),
      activity: `User id ${userId} melihat semua enrollment`,
      ip_address: req.ip,
    });

    return res.status(200).json({
      status: "success",
      data: enrollments,
    });
  } catch (error) {
    console.error("CourseEnrollmentsController.getAllEnrollments", error);
    return res.status(500).json({
      status: "error",
      message: "Failed to get all enrollments",
      details: error.message,
    });
  }
};

const getEnrollmentById = async (req, res) => {
  try {
    const enrollmentId = req.params.enrollmentId || req.params.id;
    const studentId = req.params.studentId || req.params.student_id;
    const userId = req.enrollmentUserId;

    if (!enrollmentId || !studentId || !userId) {
      return res.status(400).json({
        status: "error",
        message: "enrollment_id, student_id, and user_id are required",
      });
    }

    const enrollment = await CourseEnrollments.findOne({
      where: {
        id: Number(enrollmentId),
        student_id: Number(studentId),
      },
    });

    if (!enrollment) {
      return res.status(404).json({
        status: "error",
        message: "Enrollment tidak ditemukan",
      });
    }

    return res.status(200).json({
      status: "success",
      data: enrollment,
    });
  } catch (error) {
    console.error("CourseEnrollmentsController.getEnrollmentById", error);
    return res.status(500).json({
      status: "error",
      message: "Failed to get enrollment",
      details: error.message,
    });
  }
};

const updateEnrollment = async (req, res) => {
  try {
    const enrollmentId = req.params.enrollmentId || req.params.id;
    const studentId = req.params.studentId || req.params.student_id;
    const userId = req.enrollmentUserId;
    const { is_bookmarked, status } = req.body;

    if (!enrollmentId || !studentId || !userId) {
      return res.status(400).json({
        status: "error",
        message: "enrollment_id, student_id, and user_id are required",
      });
    }

    const enrollment = await CourseEnrollments.findOne({
      where: {
        id: Number(enrollmentId),
        student_id: Number(studentId),
      },
    });

    if (!enrollment) {
      return res.status(404).json({
        status: "error",
        message: "Enrollment tidak ditemukan",
      });
    }

    if (is_bookmarked !== undefined) enrollment.is_bookmarked = is_bookmarked;
    if (status) enrollment.status = status;

    await enrollment.save();

    await ActivityLogs.create({
      user_id: Number(userId),
      activity: `User id ${userId} memperbarui enrollment id ${enrollmentId}`,
      ip_address: req.ip,
    });

    return res.status(200).json({
      status: "success",
      data: enrollment,
    });
  } catch (error) {
    console.error("CourseEnrollmentsController.updateEnrollment", error);
    return res.status(500).json({
      status: "error",
      message: "Failed to update enrollment",
      details: error.message,
    });
  }
};

const deleteEnrollment = async (req, res) => {
  try {
    const enrollmentId = req.params.enrollmentId || req.params.id;
    const studentId = req.params.studentId || req.params.student_id;
    const userId = req.enrollmentUserId;

    if (!enrollmentId || !studentId || !userId) {
      return res.status(400).json({
        status: "error",
        message: "enrollment_id, student_id, and user_id are required",
      });
    }

    const enrollment = await CourseEnrollments.findOne({
      where: {
        id: Number(enrollmentId),
        student_id: Number(studentId),
      },
    });

    if (!enrollment) {
      return res.status(404).json({
        status: "error",
        message: "Enrollment tidak ditemukan",
      });
    }

    await enrollment.destroy();

    await ActivityLogs.create({
      user_id: Number(userId),
      activity: `User id ${userId} menghapus enrollment id ${enrollmentId}`,
      ip_address: req.ip,
    });

    return res.status(200).json({
      status: "success",
      data: {
        id: Number(enrollmentId),
        deleted: true,
      },
    });
  } catch (error) {
    console.error("CourseEnrollmentsController.deleteEnrollment", error);
    return res.status(500).json({
      status: "error",
      message: "Failed to delete enrollment",
      details: error.message,
    });
  }
};

module.exports = {
  createEnrollment,
  getEnrollmentsByStudent,
  getAllEnrollments,
  getEnrollmentById,
  updateEnrollment,
  deleteEnrollment,
};

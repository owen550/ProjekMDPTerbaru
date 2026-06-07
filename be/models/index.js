const Users = require("./Users");
const Payments = require("./Payments");
const Courses = require("./Courses");
const CourseEnrollments = require("./CourseEnrollments"); 
const CourseTopics = require("./CourseTopics"); 
const TopicMaterials = require("./TopicMaterials"); 
const Quizzes = require("./Quizzes");
const QuizQuestions = require("./QuizQuestions");
const StudentSubmissions = require("./StudentSubmissions");
const CsChatbotChats = require("./CsChatbotChats");
const ActivityLogs = require("./ActivityLogs");
const AdminMessages = require("./AdminMessages");

// ============================================================================
// RELASI/ASOSIASI ANTAR TABEL
// ============================================================================

// 1. Relasi Users <-> Payments
Users.hasMany(Payments, { foreignKey: "user_id" });
Payments.belongsTo(Users, { foreignKey: "user_id" });

// 2. Relasi Users <-> Courses (Guru membuat kelas)
Users.hasMany(Courses, { foreignKey: "teacher_id", as: "created_courses" });
Courses.belongsTo(Users, { foreignKey: "teacher_id", as: "teacher" });

// 3. Relasi Many-to-Many Users <-> Courses LEWAT CourseEnrollments (Siswa daftar kelas)
Users.belongsToMany(Courses, {
  through: CourseEnrollments, // maksutnya user punya banyak kursus, relasi mereka membentuk model bernama CourseEnrollments
  foreignKey: "student_id",
  as: "enrolled_courses"
});
Courses.belongsToMany(Users, {
  through: CourseEnrollments,
  foreignKey: "course_id",
  as: "students" 
});

// Relasi opsional tambahan agar bisa query langsung ke tabel jembatannya
Users.hasMany(CourseEnrollments, { foreignKey: "student_id" });
CourseEnrollments.belongsTo(Users, { foreignKey: "student_id", as: "student" });

Courses.hasMany(CourseEnrollments, { foreignKey: "course_id" });
CourseEnrollments.belongsTo(Courses, { foreignKey: "course_id", as: "course" });

// 4. Relasi Courses <-> CourseTopics (Satu kelas punya banyak topik pertemuan)
Courses.hasMany(CourseTopics, { foreignKey: "course_id", as: "topics" });
CourseTopics.belongsTo(Courses, { foreignKey: "course_id", as: "course" });

// 5 
CourseTopics.hasOne(TopicMaterials, { foreignKey: "topic_id", as: "material" });
TopicMaterials.belongsTo(CourseTopics, { foreignKey: "topic_id", as: "topic" });

// 6. Relasi CourseTopics <-> Quizzes (Satu topik punya satu detail kuis)
CourseTopics.hasOne(Quizzes, { foreignKey: "topic_id", as: "quiz" });
Quizzes.belongsTo(CourseTopics, { foreignKey: "topic_id", as: "topic" });

// 7. Relasi Quizzes <-> QuizQuestions (Satu kuis bisa memiliki banyak butir soal)
Quizzes.hasMany(QuizQuestions, { foreignKey: "quiz_id", as: "questions" });
QuizQuestions.belongsTo(Quizzes, { foreignKey: "quiz_id", as: "quiz" });

// 8. Relasi Quizzes <-> StudentSubmissions (Satu kuis bisa menerima banyak pengumpulan jawaban dari berbagai siswa)
Quizzes.hasMany(StudentSubmissions, { foreignKey: "quiz_id", as: "submissions" });
StudentSubmissions.belongsTo(Quizzes, { foreignKey: "quiz_id", as: "quiz" });

// 9. Relasi Users <-> StudentSubmissions (Satu siswa bisa mengumpulkan banyak tugas di aplikasi)
Users.hasMany(StudentSubmissions, { foreignKey: "student_id", as: "my_submissions" });
StudentSubmissions.belongsTo(Users, { foreignKey: "student_id", as: "student" });

// 10. Relasi Users <-> CsChatbotChats (User bisa memiliki banyak riwayat chat dengan AI)
Users.hasMany(CsChatbotChats, { foreignKey: "user_id", as: "bot_chats" });
CsChatbotChats.belongsTo(Users, { foreignKey: "user_id", as: "user" });

// 11. Relasi Users <-> ActivityLogs (Satu user memiliki banyak jejak log aktivitas)
Users.hasMany(ActivityLogs, { foreignKey: "user_id", as: "logs" });
ActivityLogs.belongsTo(Users, { foreignKey: "user_id", as: "user" });

// 12. Relasi Ganda Users <-> AdminMessages
// Hubungan untuk Pengirim (Admin)
Users.hasMany(AdminMessages, { foreignKey: "admin_id", as: "sent_messages" });
AdminMessages.belongsTo(Users, { foreignKey: "admin_id", as: "sender_admin" });

// Hubungan untuk Penerima (Guru/Murid)
Users.hasMany(AdminMessages, { foreignKey: "receiver_id", as: "received_messages" });
AdminMessages.belongsTo(Users, { foreignKey: "receiver_id", as: "receiver" });

// ============================================================================

module.exports = {
  Users,
  Payments,
  Courses,
  CourseEnrollments,
  CourseTopics,
  TopicMaterials,
  Quizzes,
  QuizQuestions,
  StudentSubmissions,
  CsChatbotChats,
  ActivityLogs,
  AdminMessages
};
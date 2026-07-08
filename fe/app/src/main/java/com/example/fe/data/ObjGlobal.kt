package com.example.fe.data

// === Users ===
data class User(
    val id: Int,
    val name: String,
    val username: String?,
    val password: String?,
    val email: String,
    val googleId: String?,
    val birthdayDate: String?,
    val role: String,
    val tier: String,
    val points: Int,
    val createdAt: String,
    val updatedAt: String,
    val deletedAt: String?
)

// === ActivityLog ===
data class ActivityLog(
    val id: Int,
    val userId: Int,
    val activity: String,
    val ipAddress: String,
    val createdAt: String
)

// === AdminMessage ===
data class AdminMessage(
    val id: Int,
    val adminId: Int,
    val receiverId: Int,
    val messageTitle: String,
    val messageBody: String,
    val isRead: Boolean,
    val createdAt: String,
    val deletedAt: String?
)

// === CourseEnrollment ===
data class CourseEnrollment(
    val id: Int,
    val studentId: Int,
    val courseId: Int,
    val isBookmarked: Boolean,
    val status: String,
    val createdAt: String,
    val updatedAt: String,
    val deletedAt: String?
)

// === Course ===
data class Course(
    val id: Int,
    val title: String,
    val category: String,
    val teacherId: Int,
    val createdAt: String,
    val updatedAt: String,
    val deletedAt: String?
)

// === CourseTopic ===
data class CourseTopic(
    val id: Int,
    val courseId: Int,
    val topicNumber: Int,
    val title: String,
    val description: String,
    val contentType: String,
    val createdAt: String,
    val deletedAt: String?
)

// === CourseTopic ===
data class CsChatbotChat(
    val id: Int,
    val userId: Int,
    val sender: String,
    val message: String,
    val createdAt: String,
    val deletedAt: String?
)

// === Payment ===
data class Payment(
    val id: Int,
    val userId: Int,
    val orderId: String,
    val paymentToken: String,
    val amount: Double,
    val paymentMethod: String,
    val vaNumber: String?,
    val qrUrl: String?,
    val status: String,
    val createdAt: String,
    val updatedAt: String,
    val deletedAt: String?
)

// === QuizQuestion ===
data class QuizQuestion(
    val id: Int,
    val quizId: Int,
    val questionText: String,
    val correctAnswer: String?,
    val createdAt: String,
    val updatedAt: String,
    val deletedAt: String?
)

// === Quiz ===
data class Quiz(
    val id: Int,
    val topicId: Int,
    val quizCategory: String,
    val questionType: String,
    val createdAt: String,
    val updatedAt: String,
    val deletedAt: String?
)

// === StudentSubmission ===
data class StudentSubmission(
    val id: Int,
    val quizId: Int,
    val studentId: Int,
    val essayAnswer: String?,
    val fileUrl: String?,
    val score: Int?,
    val teacherComment: String?,
    val status: String,
    val createdAt: String,
    val updatedAt: String,
    val deletedAt: String?
)

// === TopicMaterial ===
data class TopicMaterial(
    val id: Int,
    val topicId: Int,
    val videoUrl: String?,
    val attachmentFile: String?,
    val createdAt: String,
    val updatedAt: String,
    val deletedAt: String?
)

// === QuizQuestionOption ===
data class QuizQuestionOption(
    val id: Int,
    val quizQuestionId: Int,
    val optionLetter: String,
    val optionText: String,
    val createdAt: String,
    val updatedAt: String,
    val deletedAt: String?
)
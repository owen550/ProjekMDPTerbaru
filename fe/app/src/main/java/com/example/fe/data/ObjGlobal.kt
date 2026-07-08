package com.example.fe.data

// === Users ===
data class User(
    val id: Int,
    val name: String,
    val username: String?,
    val password: String?,
    val email: String,
    val google_id: String?,
    val birthday_date: String?,
    val role: String,
    val tier: String,
    val points: Int,
    val created_at: String,
    val updated_at: String,
    val deleted_at: String?
)

// === ActivityLog ===
data class ActivityLog(
    val id: Int,
    val user_id: Int,
    val activity: String,
    val ip_address: String,
    val created_at: String
)

// === AdminMessage ===
data class AdminMessage(
    val id: Int,
    val admin_id: Int,
    val receiver_id: Int,
    val message_title: String,
    val message_body: String,
    val is_read: Boolean,
    val created_at: String,
    val deleted_at: String?
)

// === CourseEnrollment ===
data class CourseEnrollment(
    val id: Int,
    val student_id: Int,
    val course_id: Int,
    val is_bookmarked: Boolean,
    val status: String,
    val created_at: String,
    val updated_at: String,
    val deleted_at: String?
)

// === Course ===
data class Course(
    val id: Int,
    val title: String,
    val category: String,
    val teacher_id: Int,
    val created_at: String,
    val updated_at: String,
    val deleted_at: String?
)

// === CourseTopic ===
data class CourseTopic(
    val id: Int,
    val course_id: Int,
    val topic_number: Int,
    val title: String,
    val description: String,
    val content_type: String,
    val created_at: String,
    val updated_at: String,
    val deleted_at: String?
)

// === CourseTopic ===
data class CsChatbotChat(
    val id: Int,
    val user_id: Int,
    val sender: String,
    val message: String,
    val created_at: String,
    val deleted_at: String?
)

// === Payment ===
data class Payment(
    val id: Int,
    val user_id: Int,
    val order_id: String,
    val payment_token: String,
    val amount: Double,
    val payment_method: String,
    val va_number: String?,
    val qr_url: String?,
    val status: String,
    val created_at: String,
    val updated_at: String,
    val deleted_at: String?
)

// === QuizQuestion ===
data class QuizQuestion(
    val id: Int,
    val quiz_id: Int,
    val question_text: String,
    val correct_answer: String?,
    val created_at: String,
    val updated_at: String,
    val deleted_at: String?
)

// === Quiz ===
data class Quizzes(
    val id: Int,
    val topic_id: Int,
    val quiz_category: String,
    val question_type: String,
    val created_at: String,
    val updated_at: String,
    val deleted_at: String?
)

// === StudentSubmission ===
data class StudentSubmission(
    val id: Int,
    val quiz_id: Int,
    val student_id: Int,
    val essay_answer: String?,
    val file_url: String?,
    val score: Int?,
    val teacher_comment: String?,
    val status: String,
    val created_at: String,
    val updated_at: String,
    val deleted_at: String?
)

// === TopicMaterial ===
data class TopicMaterial(
    val id: Int,
    val topic_id: Int,
    val video_url: String?,
    val attachment_file: String?,
    val created_at: String,
    val updated_at: String,
    val deleted_at: String?
)

// === QuizQuestionOption ===
data class QuizQuestionOption(
    val id: Int,
    val quiz_question_id: Int,
    val option_letter: String,
    val option_text: String,
    val created_at: String,
    val updated_at: String,
    val deleted_at: String?
)

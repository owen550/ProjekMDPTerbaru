package com.example.fe.data.repositories

import com.example.fe.data.ActivityLog
import com.example.fe.data.AdminMessage
import com.example.fe.data.Course
import com.example.fe.data.CourseEnrollment
import com.example.fe.data.CourseTopic
import com.example.fe.data.CsChatbotChat
import com.example.fe.data.Payment
import com.example.fe.data.QuizQuestion
import com.example.fe.data.QuizQuestionOption
import com.example.fe.data.Quizzes
import com.example.fe.data.StudentSubmission
import com.example.fe.data.TopicMaterial
import com.example.fe.data.User

interface TodoRepository {

    // ============================
    // User
    // ============================

    suspend fun getAllUser(userId: Int): Result<List<User>>
    suspend fun addUser(user: User): Result<User>
    suspend fun getUserById(
        userId: Int,
        userPencariId: Int
    ): Result<User>
    suspend fun updateUser(user: User): Result<User>
    suspend fun deleteUser(userId: Int): Result<User>
    suspend fun doLogin(
        usernameoremail: String,
        password: String
    ): Result<User>

    // =======================
    // Course
    // =======================

    suspend fun getAllCourses(userId: Int): Result<List<Course>>

    suspend fun getCourseById(userId: Int, courseId: Int): Result<Course>

    suspend fun insertCourse(userId: Int, title: String, category: String): Result<Course>

    suspend fun updateCourse(userId: Int, title: String, category: String, courseId: Int): Result<Course>

    suspend fun deleteCourse(): Result<Unit>

    // =======================
    // Course Topic
    // =======================

    suspend fun getAllTopics(
        userid: Int,
        courseid: Int
    ): Result<List<CourseTopic>>

    suspend fun insertTopic(
        userId: Int,
        courseId: Int,
        topicNumber: Int,
        title: String,
        description: String,
        contentType: String
    ): Result<CourseTopic>

    suspend fun updateTopic(
        userId: Int,
        topicNumber: Int,
        description: String,
        title: String
    ): Result<CourseTopic>

    suspend fun deleteTopic(): Result<Unit>

    // =======================
    // Topic Material
    // =======================

    suspend fun getAllMaterials(
        userId: Int
    ): Result<List<TopicMaterial>>

    suspend fun getMaterialById(
        userId: Int,
        topic_id: Int
    ): Result<TopicMaterial>

    suspend fun insertMaterial(
        userId: Int,
        videoUrl: String,
        attachmentFile: String,
        topicId: Int,
    ): Result<TopicMaterial>

    suspend fun updateMaterial(
        userId: Int,
        topicId: Int,
        videoUrl: String,
        attachmentFile: String
    ): Result<TopicMaterial>

    suspend fun deleteMaterial(topicId: Int): Result<Unit>

    // =======================
    // Admin Message
    // =======================

    suspend fun sendMessageFromAdmin(
        adminId: Int,
        receiverId: Int,
        title: String,
        body: String
    ): Result<AdminMessage>

    suspend fun sendMessageFromUser(
        userId: Int,
        adminId: Int,
        title: String,
        body: String
    ): Result<AdminMessage>

    suspend fun getMessagesById(userId: Int, adminId: Int): Result<List<AdminMessage>>

    suspend fun getAdminChatList(adminId: Int): Result<List<AdminMessage>>

    // =======================
    // Quizzes
    // =======================

    suspend fun getAllQuizzes(userId: Int): Result<List<Quizzes>>

    suspend fun getQuizById(userId: Int, quizId: Int): Result<Quizzes>

    suspend fun insertQuiz(
        userId: Int,
        topicId: Int,
        category: String,
        questionType: String
    ): Result<Quizzes>

    suspend fun updateQuiz(
        userId: Int,
        category: String,
        questionType: String,
        id: Int,
    ): Result<Quizzes>

    suspend fun deleteQuiz(id: Int): Result<Unit>

    // =======================
    // Quiz Question
    // =======================

    suspend fun getAllQuestions(userId: Int): Result<List<QuizQuestion>>

    suspend fun getQuestionById(userId: Int, questionId: Int): Result<QuizQuestion>

    suspend fun insertQuestion(
        userId: Int,
        quizId: Int,
        questionText: String,
        correctAnswer: String,
    ): Result<QuizQuestion>

    suspend fun updateQuestion(
        userId: Int,
        quizCategory: String,
        questionType: String,
        id: Int,
    ): Result<QuizQuestion>

    suspend fun deleteQuestion(userId: Int, id: Int): Result<Unit>

    // =======================
    // Quiz Question Option
    // =======================

    suspend fun getAllOptions(userId: Int): Result<List<QuizQuestionOption>>

    suspend fun getOptionById(userId: Int, optionId: Int): Result<QuizQuestionOption>

    suspend fun insertOption(
        userId: Int,
        quizId: Int,
        questionText: String,
        correctAnswer: String,
    ): Result<QuizQuestionOption>

    suspend fun updateOption(
        userId: Int,
        id: Int,
        optionLetter: String,
        optionText: String,
    ): Result<QuizQuestionOption>

    suspend fun deleteOption(userId: Int, id: Int): Result<Unit>

    // =======================
    // Chatbot
    // =======================

    suspend fun createChat(userId: Int, sender: String, message: String): Result<CsChatbotChat>

    suspend fun getAllChats(userId: Int): Result<List<CsChatbotChat>>

    suspend fun getChats(userId: Int): Result<List<CsChatbotChat>>

    suspend fun getChatDetail(userId: Int, chatId: Int): Result<CsChatbotChat>

    suspend fun updateChat(userId: Int, chatId: Int, message: String): Result<CsChatbotChat>

    suspend fun deleteChat(userId: Int, chatId: Int): Result<Unit>

    // =======================
    // Payment
    // =======================

    suspend fun createPayment(
        userId: Int,
        orderId: String,
        paymentToken: String,
        amount: Double,
        paymentMethod: String,
        vaNumber: String? = null,
        qrUrl: String? = null,
        status: String? = null
    ): Result<Payment>

    suspend fun getAllPayments(userId: Int): Result<List<Payment>>

    suspend fun getPayments(userId: Int): Result<List<Payment>>

    suspend fun getPaymentDetail(userId: Int, paymentId: Int): Result<Payment>

    suspend fun updatePayment(
        userId: Int,
        paymentId: Int,
        orderId: String,
        paymentToken: String,
        amount: Double,
        paymentMethod: String,
        status: String? = null,
        vaNumber: String? = null,
        qrUrl: String? = null
    ): Result<Payment>

    suspend fun deletePayment(userId: Int, paymentId: Int): Result<Unit>

    // =======================
    // Course Enrollment
    // =======================

    suspend fun createEnrollment(
        userId: Int,
        studentId: Int,
        studentIdBody: Int,
        courseId: Int,
        isBookmarked: Boolean?,
        status: String?
    ): Result<CourseEnrollment>

    suspend fun getAllEnrollments(userId: Int): Result<List<CourseEnrollment>>

    suspend fun getEnrollmentByStudent(userId: Int, studentId: Int): Result<List<CourseEnrollment>>

    suspend fun getEnrollmentDetail(userId: Int, studentId: Int, enrollmentId: Int): Result<CourseEnrollment>

    suspend fun updateEnrollment(
        userId: Int,
        studentId: Int,
        enrollmentId: Int,
        studentIdBody: Int,
        courseId: Int,
        isBookmarked: Boolean?,
        status: String?
    ): Result<CourseEnrollment>

    suspend fun deleteEnrollment(userId: Int, studentId: Int, enrollmentId: Int): Result<Unit>

    // =======================
    // Student Submission
    // =======================

    suspend fun getAllSubmissions(userId: Int): Result<List<StudentSubmission>>

    suspend fun getSubmissionById(userId: Int, submissionId: Int): Result<StudentSubmission>

    suspend fun insertSubmission(
        userId: Int,
        quizId: Int,
        studentId: Int,
        essayAnswer: String,
        fileUrl: String?,
        score: Int?,
        teacherComment: String?,
        status: String?
    ): Result<StudentSubmission>

    suspend fun updateSubmission(
        userId: Int,
        submissionId: Int,
        essayAnswer: String?,
        fileUrl: String?,
        score: Int?,
        teacherComment: String?,
        status: String?
    ): Result<StudentSubmission>

    suspend fun deleteSubmission(userId: Int, submissionId: Int): Result<Unit>

    // =======================
    // Activity Log (Admin)
    // =======================

    suspend fun getAllActivityLogs(userId: Int): Result<List<ActivityLog>>
}

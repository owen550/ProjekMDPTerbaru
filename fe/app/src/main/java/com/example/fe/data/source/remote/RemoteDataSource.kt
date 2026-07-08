package com.example.fe.data.remote

import com.example.fe.data.*

interface RemoteDataSource {

    // =======================
    // User
    // =======================
    suspend fun getAllUser(): Result<List<User>>
    suspend fun addUser(user: User): Result<User>
    suspend fun updateUser(user: User): Result<User>
    suspend fun getUserById(id: Int): Result<User>
    suspend fun deleteUser(id: Int): Result<Boolean>

    // =======================
    // Course
    // =======================
    suspend fun getAllCourse(): Result<List<Course>>
    suspend fun insertCourse(course: Course): Result<Course>
    suspend fun updateCourse(course: Course): Result<Course>
    suspend fun getCourseById(id: Int): Result<Course>
    suspend fun deleteCourse(id: Int): Result<Boolean>

    // =======================
    // Course Topic
    // =======================
    suspend fun getAllCourseTopic(): Result<List<CourseTopic>>
    suspend fun insertCourseTopic(topic: CourseTopic): Result<CourseTopic>
    suspend fun updateCourseTopic(topic: CourseTopic): Result<CourseTopic>
    suspend fun deleteCourseTopic(id: Int): Result<Boolean>

    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    // =======================
    // Topic Material
    // =======================
    suspend fun getAllTopicMaterial(): Result<List<TopicMaterial>>
    suspend fun getTopicMaterialByTopicId(topicId: Int): Result<List<TopicMaterial>>
    suspend fun insertTopicMaterial(topicMaterial: TopicMaterial): Result<TopicMaterial>
    suspend fun updateTopicMaterial(topicMaterial: TopicMaterial): Result<TopicMaterial>
    suspend fun deleteTopicMaterial(id: Int): Result<Boolean>

    // =======================
    // Quiz
    // =======================
    suspend fun getAllQuiz(): Result<List<Quiz>>
    suspend fun getAllQuizCopy(): Result<List<Quiz>>
    suspend fun insertQuiz(quiz: Quiz): Result<Quiz>
    suspend fun updateQuiz(quiz: Quiz): Result<Quiz>
    suspend fun deleteQuiz(id: Int): Result<Boolean>

    // =======================
    // Quiz Question
    // =======================
    suspend fun getAllQuizQuestion(): Result<List<QuizQuestion>>
    suspend fun getQuizQuestionById(id: Int): Result<QuizQuestion>
    suspend fun insertQuizQuestion(quizQuestion: QuizQuestion): Result<QuizQuestion>
    suspend fun updateQuizQuestion(quizQuestion: QuizQuestion): Result<QuizQuestion>
    suspend fun deleteQuizQuestion(id: Int): Result<Boolean>

    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    // =======================
    // Quiz Question Option
    // =======================
    suspend fun getAllQuizQuestionOption(): Result<List<QuizQuestionOption>>
    suspend fun getQuizQuestionOptionById(id: Int): Result<QuizQuestionOption>
    suspend fun insertQuizQuestionOption(option: QuizQuestionOption): Result<QuizQuestionOption>
    suspend fun updateQuizQuestionOption(option: QuizQuestionOption): Result<QuizQuestionOption>
    suspend fun deleteQuizQuestionOption(id: Int): Result<Boolean>

    // =======================
    // Student Submission
    // =======================
    suspend fun getAllStudentSubmission(): Result<List<StudentSubmission>>
    suspend fun getStudentSubmissionById(id: Int): Result<StudentSubmission>
    suspend fun insertStudentSubmission(submission: StudentSubmission): Result<StudentSubmission>
    suspend fun updateStudentSubmission(submission: StudentSubmission): Result<StudentSubmission>
    suspend fun deleteStudentSubmission(id: Int): Result<Boolean>


    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    // =======================
    // Admin Messages
    // =======================
    suspend fun sendAdminMessage(
        adminId: Int,
        userId: Int,
        messageTitle: String,
        messageBody: String
    ): Result<AdminMessage>
    suspend fun sendUserMessage(
        userId: Int,
        adminId: Int,
        messageTitle: String,
        messageBody: String
    ): Result<AdminMessage>
    suspend fun getMessagesByUserId(userId: Int, adminId: Int): Result<List<AdminMessage>>
    suspend fun getUsersByAdminId(adminId: Int): Result<List<User>>


    // =======================
    // Chatbot Chats
    // =======================
    suspend fun createChatbotChat(
        userId: Int,
        chatData: Map<String, Any>
    ): Result<CsChatbotChat>
    suspend fun getAllChatbotChats(userId: Int): Result<List<CsChatbotChat>>
    suspend fun getChatbotChatsByUser(userId: Int): Result<List<CsChatbotChat>>
    suspend fun getChatbotChatById(userId: Int, chatId: Int): Result<CsChatbotChat>
    suspend fun updateChatbotChat(
        userId: Int,
        chatId: Int,
        chatData: Map<String, Any>
    ): Result<CsChatbotChat>
    suspend fun deleteChatbotChat(userId: Int, chatId: Int): Result<Boolean>


    // =======================
    // Payments
    // =======================
    suspend fun createPayment(
        userId: Int,
        paymentData: Map<String, Any>
    ): Result<Payment>
    suspend fun getAllPayments(userId: Int): Result<List<Payment>>
    suspend fun getPaymentsByUser(userId: Int): Result<List<Payment>>
    suspend fun getPaymentById(userId: Int, paymentId: Int): Result<Payment>
    suspend fun updatePayment(
        userId: Int,
        paymentId: Int,
        paymentData: Map<String, Any>
    ): Result<Payment>
    suspend fun deletePayment(userId: Int, paymentId: Int): Result<Boolean>


    // =======================
    // Course Enrollments
    // =======================
    suspend fun createEnrollment(
        userId: Int,
        studentId: Int,
        enrollmentData: Map<String, Any>
    ): Result<CourseEnrollment>
    suspend fun getAllEnrollments(userId: Int): Result<List<CourseEnrollment>>
    suspend fun getEnrollmentsByStudent(userId: Int, studentId: Int): Result<List<CourseEnrollment>>
    suspend fun getEnrollmentById(
        userId: Int,
        studentId: Int,
        enrollmentId: Int
    ): Result<CourseEnrollment>
    suspend fun updateEnrollment(
        userId: Int,
        studentId: Int,
        enrollmentId: Int,
        enrollmentData: Map<String, Any>
    ): Result<CourseEnrollment>
    suspend fun deleteEnrollment(
        userId: Int,
        studentId: Int,
        enrollmentId: Int
    ): Result<Boolean>

}
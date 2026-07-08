package com.example.fe.data.remote

import com.example.fe.data.*

interface RemoteDataSource {
    // =======================
    // User
    // =======================
    suspend fun getAllUser(userId: Int): Result<List<User>>
    suspend fun addUser(
        name: String,
        username: String,
        passwordUser: String,
        email: String,
        googleId: String? = null,
        birthdayDate: String? = null,
        role: String,
        tier: String? = null
    ): Result<User>
    suspend fun getUserById(userId: Int, userPencariId: Int): Result<User>
    suspend fun updateUser(
        userId: Int,
        name: String,
        username: String,
        email: String,
        role: String,
        tier: String
    ): Result<User>
    suspend fun deleteUser(userId: Int): Result<Boolean>


    // =======================
    // Course
    // =======================
    suspend fun getAllCourse(userId: Int): Result<List<Course>>
    suspend fun getCourseById(userId: Int, courseId: Int): Result<Course>
    suspend fun insertCourse(
        userId: Int,
        title: String,
        category: String // Enum: 'mathematics', 'science', 'art', 'technology', 'social'
    ): Result<Course>
    suspend fun updateCourse(
        userId: Int,
        courseId: Int,
        title: String,
        category: String
    ): Result<Course>
    suspend fun deleteCourse(userId: Int, courseId: Int): Result<Boolean>


    // =======================
    // Course Topic
    // =======================
    suspend fun getAllCourseTopic(userId: Int, courseId: Int): Result<List<CourseTopic>>
    suspend fun insertCourseTopic(
        userId: Int,
        courseId: Int,
        topicNumber: Int,
        title: String,
        description: String,
        contentType: String // 'material' atau 'quiz'
    ): Result<CourseTopic>
    suspend fun updateCourseTopic(
        userId: Int,
        courseTopicId: Int,
        title: String,
        description: String
    ): Result<CourseTopic>
    suspend fun deleteCourseTopic(userId: Int, courseTopicId: Int): Result<Boolean>


    // =======================
    // Topic Material
    // =======================
    suspend fun getAllTopicMaterial(userId: Int): Result<List<TopicMaterial>>
    suspend fun getTopicMaterialById(userId: Int, topicId: Int): Result<TopicMaterial>
    suspend fun insertTopicMaterial(
        userId: Int,
        topicId: Int,
        videoUrl: String,
        attachmentFile: String
    ): Result<TopicMaterial>
    suspend fun updateTopicMaterial(
        userId: Int,
        topicId: Int,
        videoUrl: String,
        attachmentFile: String
    ): Result<TopicMaterial>
    suspend fun deleteTopicMaterial(userId: Int, topicId: Int): Result<Boolean>


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
    // Quizzes
    // =======================
    suspend fun getAllQuizzes(userId: Int): Result<List<Quiz>>
    suspend fun getQuizzesById(userId: Int, quizzId: Int): Result<Quiz>
    suspend fun insertQuizzes(
        userId: Int,
        topicId: Int,
        quizCategory: String,
        questionType: String
    ): Result<Quiz>
    suspend fun updateQuizzes(
        userId: Int,
        quizzId: Int,
        quizCategory: String,
        questionType: String
    ): Result<Quiz>
    suspend fun deleteQuizzes(userId: Int, quizzId: Int): Result<Boolean>


    // =======================
    // Quiz Questions
    // =======================
    suspend fun getAllQuizQuestions(userId: Int): Result<List<QuizQuestion>>
    suspend fun getQuizQuestionById(userId: Int, questionId: Int): Result<QuizQuestion>
    suspend fun insertQuizQuestion(
        userId: Int,
        questionData: Map<String, Any>
    ): Result<QuizQuestion>
    suspend fun updateQuizQuestion(
        userId: Int,
        questionId: Int,
        questionData: Map<String, Any>
    ): Result<QuizQuestion>
    suspend fun deleteQuizQuestion(userId: Int, questionId: Int): Result<Boolean>


    // =======================
    // Quiz Question Options
    // =======================
    suspend fun getAllQuizQuestionOptions(userId: Int): Result<List<QuizQuestionOption>>
    suspend fun getQuizQuestionOptionById(userId: Int, optionId: Int): Result<QuizQuestionOption>
    suspend fun insertQuizQuestionOption(
        userId: Int,
        optionData: Map<String, Any>
    ): Result<QuizQuestionOption>
    suspend fun updateQuizQuestionOption(
        userId: Int,
        optionId: Int,
        optionData: Map<String, Any>
    ): Result<QuizQuestionOption>
    suspend fun deleteQuizQuestionOption(userId: Int, optionId: Int): Result<Boolean>


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


    // =======================
    // Student Submissions
    // =======================
    suspend fun getAllStudentSubmissions(userId: Int): Result<List<StudentSubmission>>
    suspend fun getStudentSubmissionById(userId: Int, submissionId: Int): Result<StudentSubmission>
    suspend fun insertStudentSubmission(
        userId: Int,
        submissionData: Map<String, Any>
    ): Result<StudentSubmission>
    suspend fun updateStudentSubmission(
        userId: Int,
        submissionId: Int,
        submissionData: Map<String, Any>
    ): Result<StudentSubmission>
    suspend fun deleteStudentSubmission(userId: Int, submissionId: Int): Result<Boolean>
}
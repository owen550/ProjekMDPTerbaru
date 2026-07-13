package com.example.fe.data.repositories

import com.example.fe.data.ActivityLog
import com.example.fe.data.AdminMessage
import com.example.fe.data.AiChatResponse
import com.example.fe.data.Course
import com.example.fe.data.CourseEnrollment
import com.example.fe.data.CourseTopic
import com.example.fe.data.CsChatbotChat
import com.example.fe.data.MidtransResponse
import com.example.fe.data.Payment
import com.example.fe.data.QuizQuestion
import com.example.fe.data.QuizQuestionOption
import com.example.fe.data.Quizzes
import com.example.fe.data.StudentSubmission
import com.example.fe.data.TopicMaterial
import com.example.fe.data.User
import com.example.fe.data.remote.RemoteDataSource
import com.example.fe.data.source.local.LocalDataSource
import com.example.fe.data.source.local.UserEntity

class DefaultTodoRepository(
    val localDataSource: LocalDataSource,
    val remoteDataSource: RemoteDataSource
) : TodoRepository {

    override suspend fun getAllUser(userId: Int): Result<List<User>> {
        return remoteDataSource.getAllUser(userId)
    }

    // === kalau room udah masukin juga ke sini
    override suspend fun addUser(
        user: User
    ): Result<User> {
        // add user ke room
        localDataSource.insert(user) // ntik ke konfert otomatis
        // add user ke ws
        return remoteDataSource.addUser(user)
    }

    override suspend fun getUserById(
        userId: Int,
        userPencariId: Int
    ): Result<User> {
        return remoteDataSource.getUserById(userId, userPencariId)
    }

    override suspend fun updateUser(user: User): Result<User> {
        var update = remoteDataSource.updateUser(user)
        .onSuccess { // kalau berhasil add ke local sotage
            localDataSource.insert(user)
        }
        return update
    }

    override suspend fun deleteUser(userId: Int): Result<User> {
        return remoteDataSource.deleteUser(userId)
    }


    override suspend fun doLogin(
        usernameoremail: String,
        password: String
    ): Result<User> {
        var teslogin = remoteDataSource.doLogin(usernameoremail, password)

        // kalau berhasil add juga ke room
        teslogin.onSuccess { userdata ->
            val userWithPassword = userdata.copy(password = password)
            localDataSource.insert(userWithPassword)
        }

        return teslogin
    }

    // ============================
    // Lokal
    // ============================

    override suspend fun getAll(): List<User> {
        return localDataSource.getAll()
    }

    override suspend fun getById(id: Int): User? {
        return localDataSource.getById(id)
    }

    override suspend fun getLastUserDESC(): User? {
        return localDataSource.getLastUserDESC()
    }

    override suspend fun insert(user: User) {
        localDataSource.insert(user)
    }

    override suspend fun update(user: User) {
        localDataSource.update(user)
    }

    override suspend fun delete(user: User) {
        localDataSource.delete(user)
    }

    // =======================
    // Course
    // =======================

    override suspend fun getAllCourses(userId: Int): Result<List<Course>> {
        return remoteDataSource.getAllCourses(userId)
    }

    override suspend fun getCourseById(userId: Int, courseId: Int): Result<Course> {
        return remoteDataSource.getCourseById(userId, courseId)
    }

    override suspend fun insertCourse(userId: Int, title: String, category: String): Result<Course> {
        return remoteDataSource.insertCourse(userId, title, category)
    }

    override suspend fun updateCourse(userId: Int, title: String, category: String, courseId: Int): Result<Course> {
        return remoteDataSource.updateCourse(userId, title, category, courseId)
    }

    override suspend fun deleteCourse(): Result<Unit> {
        return remoteDataSource.deleteCourse()
    }

    // =======================
    // Course Topic
    // =======================

    override suspend fun getAllTopics(
        userid: Int,
        courseid: Int
    ): Result<List<CourseTopic>> {
        return remoteDataSource.getAllTopics(userid,courseid)
    }

    override suspend fun insertTopic(
        userId: Int,
        courseId: Int,
        topicNumber: Int,
        title: String,
        description: String,
        contentType: String
    ): Result<CourseTopic> {
        return remoteDataSource.insertTopic(userId, courseId, topicNumber, title, description, contentType)
    }

    override suspend fun updateTopic(
        userId: Int,
        topicNumber: Int,
        description: String,
        title: String
    ): Result<CourseTopic> {
        return remoteDataSource.updateTopic(userId, topicNumber, description, title)
    }

    override suspend fun deleteTopic(): Result<Unit> {
        return remoteDataSource.deleteTopic()
    }

    // =======================
    // Topic Material
    // =======================

    override suspend fun getAllMaterials(userId: Int): Result<List<TopicMaterial>> {
        return remoteDataSource.getAllMaterials(userId)
    }

    override suspend fun getMaterialById(
        userId: Int,
        topic_id: Int
    ): Result<TopicMaterial> {
        return remoteDataSource.getMaterialById(userId,topic_id)
    }

    override suspend fun insertMaterial(
        userId: Int,
        videoUrl: String,
        attachmentFile: String,
        topicId: Int
    ): Result<TopicMaterial> {
        return remoteDataSource.insertMaterial(userId, videoUrl, attachmentFile, topicId)
    }

    override suspend fun updateMaterial(
        userId: Int,
        topicId: Int,
        videoUrl: String,
        attachmentFile: String
    ): Result<TopicMaterial> {
        return remoteDataSource.updateMaterial(userId, topicId, videoUrl, attachmentFile)
    }

    override suspend fun deleteMaterial(topicId: Int): Result<Unit> {
        return remoteDataSource.deleteMaterial(topicId)
    }

    // =======================
    // Admin Message
    // =======================

    override suspend fun sendMessageFromAdmin(
        adminId: Int,
        receiverId: Int,
        title: String,
        body: String
    ): Result<AdminMessage> {
        return remoteDataSource.sendMessageFromAdmin(adminId, receiverId, title, body)
    }

    override suspend fun sendMessageFromUser(
        userId: Int,
        adminId: Int,
        title: String,
        body: String
    ): Result<AdminMessage> {
        return remoteDataSource.sendMessageFromUser(userId, adminId, title, body)
    }

    override suspend fun getMessagesById(userId: Int, adminId: Int): Result<List<AdminMessage>> {
        return remoteDataSource.getMessagesById(userId, adminId)
    }

    override suspend fun getAdminChatList(adminId: Int): Result<List<AdminMessage>> {
        return remoteDataSource.getAdminChatList(adminId)
    }

    // =======================
    // Quizzes
    // =======================

    override suspend fun getAllQuizzes(userId: Int, topicId: Int): Result<List<Quizzes>> {
        return remoteDataSource.getAllQuizzes(userId, topicId)
    }

    override suspend fun getQuizById(userId: Int, quizId: Int): Result<Quizzes> {
        return remoteDataSource.getQuizById(userId, quizId)
    }

    override suspend fun insertQuiz(
        userId: Int,
        topicId: Int,
        category: String,
        questionType: String
    ): Result<Quizzes> {
        return remoteDataSource.insertQuiz(userId, topicId, category, questionType)
    }

    override suspend fun updateQuiz(
        userId: Int,
        category: String,
        questionType: String,
        id: Int
    ): Result<Quizzes> {
        return remoteDataSource.updateQuiz(userId, category, questionType, id)
    }

    override suspend fun deleteQuiz(userId: Int, id: Int): Result<Unit> {
        return remoteDataSource.deleteQuiz(userId, id)
    }

    // =======================
    // Quiz Question
    // =======================

    override suspend fun getAllQuestions(userId: Int, quizId: Int): Result<List<QuizQuestion>> {
        return remoteDataSource.getAllQuestions(userId, quizId)
    }

    override suspend fun getQuestionById(userId: Int, questionId: Int): Result<QuizQuestion> {
        return remoteDataSource.getQuestionById(userId, questionId)
    }

    override suspend fun insertQuestion(
        userId: Int,
        quizId: Int,
        questionText: String,
        correctAnswer: String
    ): Result<QuizQuestion> {
        return remoteDataSource.insertQuestion(userId, quizId, questionText, correctAnswer)
    }

    override suspend fun updateQuestion(
        userId: Int,
        id: Int,
        questionText: String,
        correctAnswer: String
    ): Result<QuizQuestion> {
        return remoteDataSource.updateQuestion(userId, id, questionText, correctAnswer)
    }

    override suspend fun deleteQuestion(userId: Int, id: Int): Result<Unit> {
        return remoteDataSource.deleteQuestion(userId, id)
    }

    // =======================
    // Quiz Question Option
    // =======================

    override suspend fun getAllOptions(userId: Int, quizId: Int): Result<List<QuizQuestionOption>> {
        return remoteDataSource.getAllOptions(userId, quizId)
    }

    override suspend fun getOptionById(userId: Int, optionId: Int): Result<QuizQuestionOption> {
        return remoteDataSource.getOptionById(userId, optionId)
    }

    override suspend fun insertOption(
        userId: Int,
        quizQuestionId: Int,
        optionLetter: String,
        optionText: String
    ): Result<QuizQuestionOption> {
        return remoteDataSource.insertOption(userId, quizQuestionId, optionLetter, optionText)
    }

    override suspend fun updateOption(
        userId: Int,
        id: Int,
        optionLetter: String,
        optionText: String
    ): Result<QuizQuestionOption> {
        return remoteDataSource.updateOption(userId, id, optionLetter, optionText)
    }

    override suspend fun deleteOption(userId: Int, id: Int): Result<Unit> {
        return remoteDataSource.deleteOption(userId, id)
    }

    // =======================
    // Chatbot
    // =======================

    override suspend fun createChat(userId: Int, sender: String, message: String): Result<CsChatbotChat> {
        return remoteDataSource.createChat(userId, sender, message)
    }

    override suspend fun getAllChats(userId: Int): Result<List<CsChatbotChat>> {
        return remoteDataSource.getAllChats(userId)
    }

    override suspend fun getChats(userId: Int): Result<List<CsChatbotChat>> {
        return remoteDataSource.getChats(userId)
    }

    override suspend fun getChatDetail(userId: Int, chatId: Int): Result<CsChatbotChat> {
        return remoteDataSource.getChatDetail(userId, chatId)
    }

    override suspend fun updateChat(userId: Int, chatId: Int, message: String): Result<CsChatbotChat> {
        return remoteDataSource.updateChat(userId, chatId, message)
    }

    override suspend fun deleteChat(userId: Int, chatId: Int): Result<Unit> {
        return remoteDataSource.deleteChat(userId, chatId)
    }

    // =======================
    // Payment
    // =======================

    override suspend fun createPayment(
        userId: Int,
        orderId: String,
        paymentToken: String,
        amount: Double,
        paymentMethod: String,
        vaNumber: String?,
        qrUrl: String?,
        status: String?
    ): Result<Payment> {
        return remoteDataSource.createPayment(userId, orderId, paymentToken, amount, paymentMethod, vaNumber, qrUrl, status)
    }

    override suspend fun getAllPayments(userId: Int): Result<List<Payment>> {
        return remoteDataSource.getAllPayments(userId)
    }

    override suspend fun getPayments(userId: Int): Result<List<Payment>> {
        return remoteDataSource.getPayments(userId)
    }

    override suspend fun getPaymentDetail(userId: Int, paymentId: Int): Result<Payment> {
        return remoteDataSource.getPaymentDetail(userId, paymentId)
    }

    override suspend fun updatePayment(
        userId: Int,
        paymentId: Int,
        orderId: String,
        paymentToken: String,
        amount: Double,
        paymentMethod: String,
        status: String?,
        vaNumber: String?,
        qrUrl: String?
    ): Result<Payment> {
        return remoteDataSource.updatePayment(userId, paymentId, orderId, paymentToken, amount, paymentMethod, status, vaNumber, qrUrl)
    }

    override suspend fun deletePayment(userId: Int, paymentId: Int): Result<Unit> {
        return remoteDataSource.deletePayment(userId, paymentId)
    }

    override suspend fun cekStatusFreePre(userid: Int): Result<Boolean> {
        return remoteDataSource.cekStatusFreePre(userid)
    }

    override suspend fun createMidtrans(
        userid: Int,
        amount: Int
    ): Result<MidtransResponse> {
        return remoteDataSource.createMidtrans(userid,amount)
    }

    override suspend fun rechekPayment(orderid: String): Result<String> {
        return remoteDataSource.rechekPayment(orderid)
    }

    // =======================
    // Course Enrollment
    // =======================

    override suspend fun createEnrollment(
        userId: Int,
        studentId: Int,
        studentIdBody: Int,
        courseId: Int,
        isBookmarked: Boolean?,
        status: String?
    ): Result<CourseEnrollment> {
        return remoteDataSource.createEnrollment(userId, studentId, studentIdBody, courseId, isBookmarked, status)
    }

    override suspend fun getAllEnrollments(userId: Int): Result<List<CourseEnrollment>> {
        return remoteDataSource.getAllEnrollments(userId)
    }

    override suspend fun getEnrollmentByStudent(userId: Int, studentId: Int): Result<List<CourseEnrollment>> {
        return remoteDataSource.getEnrollmentByStudent(userId, studentId)
    }

    override suspend fun getEnrollmentDetail(userId: Int, studentId: Int, enrollmentId: Int): Result<CourseEnrollment> {
        return remoteDataSource.getEnrollmentDetail(userId, studentId, enrollmentId)
    }

    override suspend fun updateEnrollment(
        userId: Int,
        studentId: Int,
        enrollmentId: Int,
        studentIdBody: Int,
        courseId: Int,
        isBookmarked: Boolean?,
        status: String?
    ): Result<CourseEnrollment> {
        return remoteDataSource.updateEnrollment(userId, studentId, enrollmentId, studentIdBody, courseId, isBookmarked, status)
    }

    override suspend fun deleteEnrollment(userId: Int, studentId: Int, enrollmentId: Int): Result<Unit> {
        return remoteDataSource.deleteEnrollment(userId, studentId, enrollmentId)
    }

    // =======================
    // Student Submission
    // =======================

    override suspend fun getAllSubmissions(userId: Int): Result<List<StudentSubmission>> {
        return remoteDataSource.getAllSubmissions(userId)
    }

    override suspend fun getSubmissionById(userId: Int, submissionId: Int): Result<StudentSubmission> {
        return remoteDataSource.getSubmissionById(userId, submissionId)
    }

    override suspend fun insertSubmission(
        userId: Int,
        quizId: Int,
        studentId: Int,
        essayAnswer: String?,
        fileUrl: String?,
        score: Int?,
        teacherComment: String?,
        status: String?
    ): Result<StudentSubmission> {
        return remoteDataSource.insertSubmission(userId, quizId, studentId, essayAnswer, fileUrl, score, teacherComment, status)
    }

    override suspend fun updateSubmission(
        userId: Int,
        submissionId: Int,
        essayAnswer: String?,
        fileUrl: String?,
        score: Int?,
        teacherComment: String?,
        status: String?
    ): Result<StudentSubmission> {
        return remoteDataSource.updateSubmission(userId, submissionId, essayAnswer, fileUrl, score, teacherComment, status)
    }

    override suspend fun deleteSubmission(userId: Int, submissionId: Int): Result<Unit> {
        return remoteDataSource.deleteSubmission(userId, submissionId)
    }

    // =======================
    // Activity Log (Admin)
    // =======================

    override suspend fun getAllActivityLogs(userId: Int): Result<List<ActivityLog>> {
        return remoteDataSource.getAllActivityLogs(userId)
    }

    //==============
    override suspend fun chatWithAi(
        role: String,
        pesan: String
    ): Result<AiChatResponse> {
        return remoteDataSource.chatWithAi(role, pesan)
    }

    // Google Auth
    // ====================

    override suspend fun doGoogleAuth(idToken: String): Result<User> {
        val googleLogin = remoteDataSource.doGoogleAuth(idToken)
        googleLogin.onSuccess { userdata ->
            // Simpan ke Room local storage
            localDataSource.insert(userdata)
        }
        return googleLogin
    }
}

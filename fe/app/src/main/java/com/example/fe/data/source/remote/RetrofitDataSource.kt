package com.example.fe.data.source.remote

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
import com.example.fe.data.remote.RemoteDataSource

class RetrofitDataSource(
    private val retrofitService: WebService
) : RemoteDataSource {

    override suspend fun getAllUser(userId: Int): Result<List<User>> {
        return try {
            val response = retrofitService.getAllUser(userId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addUser(user: User): Result<User> {
        return try {
            val response = retrofitService.addUser(
                name = user.name,
                username = user.username,
                password = user.password,
                email = user.email,
                googleId = user.google_id,
                birthdayDate = user.birthday_date,
                role = user.role,
                tier = user.tier
            )

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUserById(
        userId: Int,
        userPencariId: Int
    ): Result<User> {
        return try {
            val response = retrofitService.getUserById(userId, userPencariId)

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateUser(user: User): Result<User> {
        return try {
            val response = retrofitService.updateUser(
                userId = user.id!!,
                name = user.name,
                username = user.username,
                password = user.password,
                email = user.email,
                googleId = user.google_id,
                birthdayDate = user.birthday_date,
                role = user.role,
                tier = user.tier
            )

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteUser(userId: Int): Result<User> {
        return try {
            val response = retrofitService.deleteUser(userId)

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun doLogin(
        usernameoremail: String,
        password: String
    ): Result<User> {
        return try {
            val response = retrofitService.doLogin(usernameoremail,password)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // =======================
    // Course
    // =======================

    override suspend fun getAllCourses(userId: Int): Result<List<Course>> {
        return try {
            val response = retrofitService.getAllCourses(userId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCourseById(userId: Int, courseId: Int): Result<Course> {
        return try {
            val response = retrofitService.getCourseById(userId, courseId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun insertCourse(userId: Int, title: String, category: String): Result<Course> {
        return try {
            val response = retrofitService.insertCourse(userId, title, category)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateCourse(userId: Int, title: String, category: String, courseId: Int): Result<Course> {
        return try {
            val response = retrofitService.updateCourse(userId, title, category, courseId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteCourse(): Result<Unit> {
        return try {
            val response = retrofitService.deleteCourse()
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // =======================
    // Course Topic
    // =======================

    override suspend fun getAllTopics(): Result<List<CourseTopic>> {
        return try {
            val response = retrofitService.getAllTopics()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun insertTopic(
        userId: Int,
        courseId: Int,
        topicNumber: Int,
        title: String,
        description: String,
        contentType: String
    ): Result<CourseTopic> {
        return try {
            val response = retrofitService.insertTopic(userId, courseId, topicNumber, title, description, contentType)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateTopic(
        userId: Int,
        topicNumber: Int,
        description: String,
        title: String
    ): Result<CourseTopic> {
        return try {
            val response = retrofitService.updateTopic(userId, topicNumber, description, title)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteTopic(): Result<Unit> {
        return try {
            val response = retrofitService.deleteTopic()
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // =======================
    // Topic Material
    // =======================

    override suspend fun getAllMaterials(userId: Int): Result<List<TopicMaterial>> {
        return try {
            val response = retrofitService.getAllMaterials(userId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMaterialById(userId: Int): Result<TopicMaterial> {
        return try {
            val response = retrofitService.getMaterialById(userId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun insertMaterial(
        userId: Int,
        videoUrl: String,
        attachmentFile: String,
        topicId: Int
    ): Result<TopicMaterial> {
        return try {
            val response = retrofitService.insertMaterial(userId, videoUrl, attachmentFile, topicId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateMaterial(
        userId: Int,
        topicId: Int,
        videoUrl: String,
        attachmentFile: String
    ): Result<TopicMaterial> {
        return try {
            val response = retrofitService.updateMaterial(userId, topicId, videoUrl, attachmentFile)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteMaterial(topicId: Int): Result<Unit> {
        return try {
            val response = retrofitService.deleteMaterial(topicId)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
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
        return try {
            val response = retrofitService.sendMessageFromAdmin(adminId, receiverId, title, body)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun sendMessageFromUser(
        userId: Int,
        adminId: Int,
        title: String,
        body: String
    ): Result<AdminMessage> {
        return try {
            val response = retrofitService.sendMessageFromUser(userId, adminId, title, body)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMessagesById(userId: Int, adminId: Int): Result<List<AdminMessage>> {
        return try {
            val response = retrofitService.getMessagesById(userId, adminId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAdminChatList(adminId: Int): Result<List<AdminMessage>> {
        return try {
            val response = retrofitService.getAdminChatList(adminId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // =======================
    // Quizzes
    // =======================

    override suspend fun getAllQuizzes(userId: Int): Result<List<Quizzes>> {
        return try {
            val response = retrofitService.getAllQuizzes(userId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getQuizById(userId: Int, quizId: Int): Result<Quizzes> {
        return try {
            val response = retrofitService.getQuizById(userId, quizId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun insertQuiz(
        userId: Int,
        topicId: Int,
        category: String,
        questionType: String
    ): Result<Quizzes> {
        return try {
            val response = retrofitService.insertQuiz(userId, topicId, category, questionType)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateQuiz(
        userId: Int,
        category: String,
        questionType: String,
        id: Int
    ): Result<Quizzes> {
        return try {
            val response = retrofitService.updateQuiz(userId, category, questionType, id)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteQuiz(id: Int): Result<Unit> {
        return try {
            val response = retrofitService.deleteQuiz(id)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // =======================
    // Quiz Questions
    // =======================

    override suspend fun getAllQuestions(userId: Int): Result<List<QuizQuestion>> {
        return try {
            val response = retrofitService.getAllQuestions(userId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getQuestionById(userId: Int, questionId: Int): Result<QuizQuestion> {
        return try {
            val response = retrofitService.getQuestionById(userId, questionId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun insertQuestion(
        userId: Int,
        quizId: Int,
        questionText: String,
        correctAnswer: String
    ): Result<QuizQuestion> {
        return try {
            val response = retrofitService.insertQuestion(userId, quizId, questionText, correctAnswer)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateQuestion(
        userId: Int,
        quizCategory: String,
        questionType: String,
        id: Int
    ): Result<QuizQuestion> {
        return try {
            val response = retrofitService.updateQuestion(userId, quizCategory, questionType, id)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteQuestion(userId: Int, id: Int): Result<Unit> {
        return try {
            val response = retrofitService.deleteQuestion(userId, id)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // =======================
    // Quiz Question Option
    // =======================

    override suspend fun getAllOptions(userId: Int): Result<List<QuizQuestionOption>> {
        return try {
            val response = retrofitService.getAllOptions(userId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getOptionById(userId: Int, optionId: Int): Result<QuizQuestionOption> {
        return try {
            val response = retrofitService.getOptionById(userId, optionId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun insertOption(
        userId: Int,
        quizId: Int,
        questionText: String,
        correctAnswer: String
    ): Result<QuizQuestionOption> {
        return try {
            val response = retrofitService.insertOption(userId, quizId, questionText, correctAnswer)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateOption(
        userId: Int,
        id: Int,
        optionLetter: String,
        optionText: String
    ): Result<QuizQuestionOption> {
        return try {
            val response = retrofitService.updateOption(userId, id, optionLetter, optionText)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteOption(userId: Int, id: Int): Result<Unit> {
        return try {
            val response = retrofitService.deleteOption(userId, id)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // =======================
    // Chatbot
    // =======================

    override suspend fun createChat(userId: Int, sender: String, message: String): Result<CsChatbotChat> {
        return try {
            val response = retrofitService.createChat(userId, sender, message)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAllChats(userId: Int): Result<List<CsChatbotChat>> {
        return try {
            val response = retrofitService.getAllChats(userId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getChats(userId: Int): Result<List<CsChatbotChat>> {
        return try {
            val response = retrofitService.getChats(userId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getChatDetail(userId: Int, chatId: Int): Result<CsChatbotChat> {
        return try {
            val response = retrofitService.getChatDetail(userId, chatId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateChat(userId: Int, chatId: Int, message: String): Result<CsChatbotChat> {
        return try {
            val response = retrofitService.updateChat(userId, chatId, message)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteChat(userId: Int, chatId: Int): Result<Unit> {
        return try {
            val response = retrofitService.deleteChat(userId, chatId)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
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
        return try {
            val response = retrofitService.createPayment(
                userId,
                orderId,
                paymentToken,
                amount,
                paymentMethod,
                vaNumber,
                qrUrl,
                status
            )

            val body = response.body()

            if (response.isSuccessful && body?.data != null) {
                Result.success(body.data!!)
            } else {
                Result.failure(Exception(body?.message ?: response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAllPayments(userId: Int): Result<List<Payment>> {
        return try {
            val response = retrofitService.getAllPayments(userId)
            val body = response.body()

            if (response.isSuccessful && body?.data != null) {
                Result.success(body.data!!)
            } else {
                Result.failure(Exception(body?.message ?: response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getPayments(userId: Int): Result<List<Payment>> {
        return try {
            val response = retrofitService.getPayments(userId)
            val body = response.body()

            if (response.isSuccessful && body?.data != null) {
                Result.success(body.data!!)
            } else {
                Result.failure(Exception(body?.message ?: response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getPaymentDetail(
        userId: Int,
        paymentId: Int
    ): Result<Payment> {
        return try {
            val response = retrofitService.getPaymentDetail(userId, paymentId)
            val body = response.body()

            if (response.isSuccessful && body?.data != null) {
                Result.success(body.data!!)
            } else {
                Result.failure(Exception(body?.message ?: response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
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
        return try {
            val response = retrofitService.updatePayment(
                userId,
                paymentId,
                orderId,
                paymentToken,
                amount,
                paymentMethod,
                status,
                vaNumber,
                qrUrl
            )

            val body = response.body()

            if (response.isSuccessful && body?.data != null) {
                Result.success(body.data!!)
            } else {
                Result.failure(Exception(body?.message ?: response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deletePayment(
        userId: Int,
        paymentId: Int
    ): Result<Unit> {
        return try {
            val response = retrofitService.deletePayment(userId, paymentId)
            val body = response.body()

            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception(body?.message ?: response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
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
        return try {
            val response = retrofitService.createEnrollment(userId, studentId, studentIdBody, courseId, isBookmarked, status)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAllEnrollments(userId: Int): Result<List<CourseEnrollment>> {
        return try {
            val response = retrofitService.getAllEnrollments(userId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getEnrollmentByStudent(userId: Int, studentId: Int): Result<List<CourseEnrollment>> {
        return try {
            val response = retrofitService.getEnrollmentByStudent(userId, studentId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getEnrollmentDetail(userId: Int, studentId: Int, enrollmentId: Int): Result<CourseEnrollment> {
        return try {
            val response = retrofitService.getEnrollmentDetail(userId, studentId, enrollmentId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
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
        return try {
            val response = retrofitService.updateEnrollment(userId, studentId, enrollmentId, studentIdBody, courseId, isBookmarked, status)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteEnrollment(userId: Int, studentId: Int, enrollmentId: Int): Result<Unit> {
        return try {
            val response = retrofitService.deleteEnrollment(userId, studentId, enrollmentId)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // =======================
    // Student Submission
    // =======================

    override suspend fun getAllSubmissions(userId: Int): Result<List<StudentSubmission>> {
        return try {
            val response = retrofitService.getAllSubmissions(userId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getSubmissionById(userId: Int, submissionId: Int): Result<StudentSubmission> {
        return try {
            val response = retrofitService.getSubmissionById(userId, submissionId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun insertSubmission(
        userId: Int,
        quizId: Int,
        studentId: Int,
        essayAnswer: String,
        fileUrl: String?,
        score: Int?,
        teacherComment: String?,
        status: String?
    ): Result<StudentSubmission> {
        return try {
            val response = retrofitService.insertSubmission(userId, quizId, studentId, essayAnswer, fileUrl, score, teacherComment, status)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
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
        return try {
            val response = retrofitService.updateSubmission(userId, submissionId, essayAnswer, fileUrl, score, teacherComment, status)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteSubmission(userId: Int, submissionId: Int): Result<Unit> {
        return try {
            val response = retrofitService.deleteSubmission(userId, submissionId)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // =======================
    // Activity Log (Admin)
    // =======================

    override suspend fun getAllActivityLogs(userId: Int): Result<List<ActivityLog>> {
        return try {
            val response = retrofitService.getAllActivityLogs(userId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

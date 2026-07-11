package com.example.fe.data.source.remote

import com.example.fe.data.ActivityLog
import com.example.fe.data.AdminMessage
import com.example.fe.data.AiChatResponse
import com.example.fe.data.Course
import com.example.fe.data.CourseEnrollment
import com.example.fe.data.CourseTopic
import com.example.fe.data.CsChatbotChat
import com.example.fe.data.DataResponse
import com.example.fe.data.Payment
import com.example.fe.data.QuizQuestion
import com.example.fe.data.QuizQuestionOption
import com.example.fe.data.Quizzes
import com.example.fe.data.StudentSubmission
import com.example.fe.data.TopicMaterial
import com.example.fe.data.User
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface WebService {

    // =======================
    // User
    // =======================

    @FormUrlEncoded
    @POST("api/users/alldata")
    suspend fun getAllUser(
        @Field("userid") userId: Int
    ): Response<List<User>>

    @FormUrlEncoded
    @POST("api/users/addusers")
    suspend fun addUser(
        @Field("name") name: String,
        @Field("username") username: String?,
        @Field("password_user") password: String?,
        @Field("email") email: String,
        @Field("google_id") googleId: String?,
        @Field("birthday_date") birthdayDate: String?,
        @Field("role") role: String,
        @Field("tier") tier: String?
    ): Response<User>

    @FormUrlEncoded
    @POST("api/users/getdatabyid")
    suspend fun getUserById(
        @Field("userid") userId: Int,
        @Field("userpencari") userPencariId: Int
    ): Response<User>

    @FormUrlEncoded
    @POST("api/users/login")
    suspend fun doLogin(
        @Field("usernameoremail") usernameoremail: String,
        @Field("password") password: String
    ): Response<User>

    @FormUrlEncoded
    @POST("api/users/updateuser") // Changed from PUT to POST if your backend uses POST for update
    suspend fun updateUser(
        @Field("userid") userId: Int,
        @Field("name") name: String,
        @Field("username") username: String?,
        @Field("password_user") password: String?,
        @Field("email") email: String,
        @Field("google_id") googleId: String?,
        @Field("birthday_date") birthdayDate: String?,
        @Field("role") role: String,
        @Field("tier") tier: String?
    ): Response<User>

    @FormUrlEncoded
    @POST("api/users/deleteuser") // Changed from PUT to POST if your backend uses POST for delete
    suspend fun deleteUser(
        @Field("userid") userId: Int
    ): Response<User>


    //COURSE==================================================

        @FormUrlEncoded
        @POST("api/course/alldata")
        suspend fun getAllCourses(
            @Field("userid") userId: Int
        ): Response<List<Course>>

        @FormUrlEncoded
        @POST("api/course/getbyid")
        suspend fun getCourseById(
            @Field("userid") userId: Int,
            @Field("courseid") courseId: Int
        ): Response<Course>

        @FormUrlEncoded
        @POST("api/course/insert")
        suspend fun insertCourse(
            @Field("userid") userId: Int,
            @Field("title") title: String,
            @Field("category") category: String
        ): Response<Course>

        @FormUrlEncoded
        @POST("api/course/update")
        suspend fun updateCourse(
            @Field("userid") userId: Int,
            @Field("title") title: String,
            @Field("category") category: String,
            @Field("courseid") courseId: Int
        ): Response<Course>

        @POST("api/course/delete")
        suspend fun deleteCourse(): Response<Unit>

    //==========================================================
    //COURSE TOPIC==================================================

        @FormUrlEncoded
        @POST("api/coursetopic/alldata")
        suspend fun getAllTopics(
            @Field("userid") userid: Int,
            @Field("courseid") courseid: Int,
        ): Response<List<CourseTopic>>

        @FormUrlEncoded
        @POST("api/coursetopic/insert")
        suspend fun insertTopic(
            @Field("userid") userId: Int,
            @Field("courseId") courseId: Int,
            @Field("topic_number") topicNumber: Int,
            @Field("title") title: String,
            @Field("description") description: String,
            @Field("content_type") contentType: String
        ): Response<CourseTopic>

        @FormUrlEncoded
        @POST("api/coursetopic/update")
        suspend fun updateTopic(
            @Field("userid") userId: Int,
            @Field("coursetopicid") topicNumber: Int,
            @Field("description") description: String,
            @Field("title") title: String
        ): Response<CourseTopic>

        @POST("api/coursetopic/delete")
        suspend fun deleteTopic(): Response<Unit>


    //==========================================================
    //Topic Material==================================================

        @FormUrlEncoded
        @POST("api/topicmaterial/alldata")
        suspend fun getAllMaterials(
            @Field("userid") userId: Int
        ): Response<List<TopicMaterial>>

        @FormUrlEncoded
        @POST("api/topicmaterial/getbyid")
        suspend fun getMaterialById(
            @Field("userid") userId: Int,
            @Field("topic_id") topic_id: Int
        ): Response<TopicMaterial>

        @FormUrlEncoded
        @POST("api/topicmaterial/insert")
        suspend fun insertMaterial(
            @Field("userid") userId: Int,
            @Field("video_url") videoUrl: String,
            @Field("attachment_file") attachmentFile: String,
            @Field("topic_id") topicId: Int,
        ): Response<TopicMaterial>

        @FormUrlEncoded
        @POST("api/topicmaterial/update")
        suspend fun updateMaterial(
            @Field("userid") userId: Int,
            @Field("topic_id") topicId: Int,
            @Field("video_url") videoUrl: String,
            @Field("attachment_file") attachmentFile: String
        ): Response<TopicMaterial>

        @FormUrlEncoded
        @POST("api/topicmaterial/delete")
        suspend fun deleteMaterial(
            @Field("topic_id") topicId: Int
        ): Response<Unit>

    //==========================================================
    //AdminMessage  =======================================

    @FormUrlEncoded
    @POST("api/adminmessages/send/admin")
    suspend fun sendMessageFromAdmin(
        @Field("admin_id") adminId: Int,
        @Field("receiver_id") receiverId: Int,
        @Field("message_title") title: String,
        @Field("message_body") body: String
    ): Response<DataResponse<AdminMessage>>


    @FormUrlEncoded
    @POST("api/adminmessages/send/user")
    suspend fun sendMessageFromUser(
        @Field("user_id") userId: Int,
        @Field("admin_id") adminId: Int,
        @Field("message_title") title: String,
        @Field("message_body") body: String
    ): Response<DataResponse<AdminMessage>>


    @GET("api/adminmessages/getbyid/{userId}/{adminId}")
    suspend fun getMessagesById(
        @Path("userId") userId: Int,
        @Path("adminId") adminId: Int
    ): Response<DataResponse<List<AdminMessage>>>


    @GET("api/adminmessages/adminchatlist/{adminId}")
    suspend fun getAdminChatList(
        @Path("adminId") adminId: Int
    ): Response<DataResponse<List<AdminMessage>>>

    //==========================================================
    //Quizzes=====================================================
        @FormUrlEncoded
        @POST("api/quizz/alldata")
        suspend fun getAllQuizzes(
            @Field("userid") userId: Int
        ): Response<List<Quizzes>>

        @FormUrlEncoded
        @POST("api/quizz/getbyid")
        suspend fun getQuizById(
            @Field("userid") userId: Int,
            @Field("quizzid") quizId: Int
        ): Response<Quizzes>

        @FormUrlEncoded
        @POST("api/quizz/insert")
        suspend fun insertQuiz(
            @Field("userid") userId: Int,
            @Field("topic_id") topicId: Int,
            @Field("quiz_category") category: String,
            @Field("question_type") questionType: String
        ): Response<Quizzes>

        @FormUrlEncoded
        @POST("api/quizz/update")
        suspend fun updateQuiz(
            @Field("userid") userId: Int,
            @Field("quiz_category") category: String,
            @Field("question_type") questionType: String,
            @Field("id") id: Int,
        ): Response<Quizzes>

        @FormUrlEncoded
        @POST("api/quizz/delete")
        suspend fun deleteQuiz(
            @Field("id") id: Int
        ): Response<Unit>

    //==========================================================
    //QuizQuestions=============================================

        @POST("api/quizquestions/alldata")
        suspend fun getAllQuestions(
            @Field("userid") userId: Int
        ): Response<List<QuizQuestion>>

        @POST("api/quizquestions/getbyid")
        suspend fun getQuestionById(
            @Field("userid") userId: Int,
            @Field("questionid") questionId: Int
        ): Response<QuizQuestion>

        @FormUrlEncoded
        @POST("api/quizquestions/insert")
        suspend fun insertQuestion(
            @Field("userid") userId: Int,
            @Field("quiz_id") quizId: Int,
            @Field("question_text") questionText: String,
            @Field("correct_answer") correctAnswer: String,
        ): Response<QuizQuestion>

        @FormUrlEncoded
        @POST("api/quizquestions/update")
        suspend fun updateQuestion(
            @Field("userid") userId: Int,
            @Field("quiz_category") quizCategory: String,
            @Field("question_type") questionType: String,
            @Field("id") id: Int,
        ): Response<QuizQuestion>

        @FormUrlEncoded
        @POST("api/quizquestions/delete")
        suspend fun deleteQuestion(
            @Field("userid") userId: Int,
            @Field("id") id: Int
        ): Response<Unit>


    //==========================================================
    //QuizQuestionOption=================================================

        @POST("api/quizquestionoptions/alldata")
        suspend fun getAllOptions(
            @Field("userid") userId: Int
        ): Response<List<QuizQuestionOption>>

        @POST("api/quizquestionoptions/getbyid")
        suspend fun getOptionById(
            @Field("userid") userId: Int,
            @Field("optionid") optionId: Int
        ): Response<QuizQuestionOption>

        @FormUrlEncoded
        @POST("api/quizquestionoptions/insert")
        suspend fun insertOption(
            @Field("userid") userId: Int,
            @Field("quiz_id") quizId: Int,
            @Field("question_text") questionText: String,
            @Field("correct_answer") correctAnswer: String,
        ): Response<QuizQuestionOption>

        @FormUrlEncoded
        @POST("api/quizquestionoptions/update")
        suspend fun updateOption(
            @Field("userid") userId: Int,
            @Field("id") id: Int,
            @Field("option_letter") optionLetter: String,
            @Field("option_text") optionText: String,

        ): Response<QuizQuestionOption>

        @FormUrlEncoded
        @POST("api/quizquestionoptions/delete")
        suspend fun deleteOption(
            @Field("userid") userId: Int,
            @Field("id") id: Int
        ): Response<Unit>

    //==========================================================
    //CHAT BOT=================================================

        @FormUrlEncoded
        @POST("api/chatbot/{userId}")
        suspend fun createChat(
            @Path("userId") userId: Int,
            @Field("sender") sender: String,
            @Field("message") message: String
        ): Response<CsChatbotChat>

        @GET("api/chatbot/all/{userId}")
        suspend fun getAllChats(
            @Path("userId") userId: Int
        ): Response<List<CsChatbotChat>>

        @GET("api/chatbot/{userId}")
        suspend fun getChats(
            @Path("userId") userId: Int
        ): Response<List<CsChatbotChat>>

        @GET("api/chatbot/{userId}/{chatId}")
        suspend fun getChatDetail(
            @Path("userId") userId: Int,
            @Path("chatId") chatId: Int
        ): Response<CsChatbotChat>

        @FormUrlEncoded
        @POST("api/chatbot/{userId}/{chatId}")
        suspend fun updateChat(
            @Path("userId") userId: Int,
            @Path("chatId") chatId: Int,
            @Field("message") message: String
        ): Response<CsChatbotChat>

        @DELETE("api/chatbot/{userId}/{chatId}")
        suspend fun deleteChat(
            @Path("userId") userId: Int,
            @Path("chatId") chatId: Int
        ): Response<Unit>
    //==========================================================
    //Payment==================================================
    @FormUrlEncoded
    @POST("api/payments/{userId}")
    suspend fun createPayment(
        @Path("userId") userId: Int,
        @Field("order_id") orderId: String,
        @Field("payment_token") paymentToken: String,
        @Field("amount") amount: Double,
        @Field("payment_method") paymentMethod: String,
        @Field("va_number") vaNumber: String? = null,
        @Field("qr_url") qrUrl: String? = null,
        @Field("status") status: String? = null
    ): Response<DataResponse<Payment>>

    @GET("api/payments/all/{userId}")
    suspend fun getAllPayments(
        @Path("userId") userId: Int
    ): Response<DataResponse<List<Payment>>>

    @GET("api/payments/{userId}")
    suspend fun getPayments(
        @Path("userId") userId: Int
    ): Response<DataResponse<List<Payment>>>

    @GET("api/payments/{userId}/{paymentId}")
    suspend fun getPaymentDetail(
        @Path("userId") userId: Int,
        @Path("paymentId") paymentId: Int
    ): Response<DataResponse<Payment>>

    @FormUrlEncoded
    @POST("api/payments/{userId}/{paymentId}")
    suspend fun updatePayment(
        @Path("userId") userId: Int,
        @Path("paymentId") paymentId: Int,
        @Field("order_id") orderId: String,
        @Field("payment_token") paymentToken: String,
        @Field("amount") amount: Double,
        @Field("payment_method") paymentMethod: String,
        @Field("status") status: String? = null,
        @Field("va_number") vaNumber: String? = null,
        @Field("qr_url") qrUrl: String? = null
    ): Response<DataResponse<Payment>>

    @DELETE("api/payments/{userId}/{paymentId}")
    suspend fun deletePayment(
        @Path("userId") userId: Int,
        @Path("paymentId") paymentId: Int
    ): Response<DataResponse<Unit>>

    //==========================================================
    //CourseEnrollment==================================================

    @FormUrlEncoded
    @POST("api/courseenrollments/{userId}/{studentId}")
    suspend fun createEnrollment(
        @Path("userId") userId: Int,
        @Path("studentId") studentId: Int,
        @Field("student_id") studentIdBody: Int,
        @Field("course_id") courseId: Int,
        @Field("is_bookmarked") isBookmarked: Boolean?,
        @Field("status") status: String?
    ): Response<DataResponse<CourseEnrollment>>

    @GET("api/courseenrollments/all/{userId}")
    suspend fun getAllEnrollments(
        @Path("userId") userId: Int
    ): Response<DataResponse<List<CourseEnrollment>>>

    @GET("api/courseenrollments/{userId}/{studentId}")
    suspend fun getEnrollmentByStudent(
        @Path("userId") userId: Int,
        @Path("studentId") studentId: Int
    ): Response<DataResponse<List<CourseEnrollment>>>

    @GET("api/courseenrollments/{userId}/{studentId}/{enrollmentId}")
    suspend fun getEnrollmentDetail(
        @Path("userId") userId: Int,
        @Path("studentId") studentId: Int,
        @Path("enrollmentId") enrollmentId: Int
    ): Response<DataResponse<CourseEnrollment>>

    @FormUrlEncoded
    @POST("api/courseenrollments/{userId}/{studentId}/{enrollmentId}")
    suspend fun updateEnrollment(
        @Path("userId") userId: Int,
        @Path("studentId") studentId: Int,
        @Path("enrollmentId") enrollmentId: Int,
        @Field("student_id") studentIdBody: Int,
        @Field("course_id") courseId: Int,
        @Field("is_bookmarked") isBookmarked: Boolean?,
        @Field("status") status: String?
    ): Response<DataResponse<CourseEnrollment>>

    @DELETE("api/courseenrollments/{userId}/{studentId}/{enrollmentId}")
    suspend fun deleteEnrollment(
        @Path("userId") userId: Int,
        @Path("studentId") studentId: Int,
        @Path("enrollmentId") enrollmentId: Int
    ): Response<DataResponse<Unit>>
//==========================================================
//StudentSubmission==================================================

    @FormUrlEncoded
    @POST("api/studentsubmissionsroutes/alldata")
    suspend fun getAllSubmissions(
        @Field("userid") userId: Int
    ): Response<List<StudentSubmission>>

    @FormUrlEncoded
    @POST("api/studentsubmissionsroutes/getbyid")
    suspend fun getSubmissionById(
        @Field("userid") userId: Int,
        @Field("submissionid") submissionId: Int
    ): Response<StudentSubmission>

    @FormUrlEncoded
    @POST("api/studentsubmissionsroutes/insert")
    suspend fun insertSubmission(
        @Field("userid") userId: Int,
        @Field("quiz_id") quizId: Int,
        @Field("student_id") studentId: Int,
        @Field("essay_answer") essayAnswer: String,
        @Field("file_url") fileUrl: String?,
        @Field("score") score: Int?,
        @Field("teacher_comment") teacherComment: String?,
        @Field("status") status: String?
    ): Response<StudentSubmission>

    @FormUrlEncoded
    @POST("api/studentsubmissionsroutes/update")
    suspend fun updateSubmission(
        @Field("userid") userId: Int,
        @Field("id") submissionId: Int,
        @Field("essay_answer") essayAnswer: String?,
        @Field("file_url") fileUrl: String?,
        @Field("score") score: Int?,
        @Field("teacher_comment") teacherComment: String?,
        @Field("status") status: String?
    ): Response<StudentSubmission>

    @FormUrlEncoded
    @POST("api/studentsubmissionsroutes/delete")
    suspend fun deleteSubmission(
        @Field("userid") userId: Int,
        @Field("id") submissionId: Int
    ): Response<Unit>

//============================================================

    // =======================
    // Activity Log (Admin)
    // =======================
    @FormUrlEncoded
    @POST("api/activitylogroutes/alldata")
    suspend fun getAllActivityLogs(
        @Field("userid") userId: Int
    ): Response<List<ActivityLog>>


    //================================================
    @FormUrlEncoded
    @POST("api/test/ai")
    suspend fun chatWithAi(
        @Field("role") role: String,
        @Field("pesan") pesan: String
    ): Response<AiChatResponse>
}

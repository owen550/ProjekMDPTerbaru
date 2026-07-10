package com.example.fe.ui.todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fe.courseTopic
import com.example.fe.data.ActivityLog
import com.example.fe.data.AdminMessage
import com.example.fe.data.Payment
import com.example.fe.data.Course
import com.example.fe.data.CourseTopic
import com.example.fe.data.CsChatbotChat
import com.example.fe.data.TopicMaterial
import com.example.fe.data.User
import com.example.fe.data.repositories.TodoRepository
import com.example.fe.materialTopic
import com.example.fe.ui.todoform.currentUserId
import com.example.fe.user
import kotlinx.coroutines.launch

class TodosViewModel(
    private val todoRepository: TodoRepository
) : ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    // === get data ====
    private val _oneuser = MutableLiveData<User?>()
    val oneuser: LiveData<User?> = _oneuser

    private val _gradesumary = MutableLiveData<GradeSummary>()
    val gradesumary: LiveData<GradeSummary> = _gradesumary

    private val _course = MutableLiveData<List<Course>>()
    val course: LiveData<List<Course>> = _course

    private val _coursedetail = MutableLiveData<List<CourseTopic>>()
    val coursedetail: LiveData<List<CourseTopic>> = _coursedetail

    private val _onetopicmaterial = MutableLiveData<TopicMaterial?>()
    val onetopicmaterial: LiveData<TopicMaterial?> = _onetopicmaterial

    private val _insertSuccess = MutableLiveData<Boolean>()
    val insertSuccess: LiveData<Boolean> = _insertSuccess

    // ==== other func =======
    fun getTopicMaterialByIDCourseTopic(
        topicId: Int
    ) {
        val userId = currentUserId
        if (userId == null) {
            _message.value = "User session invalid"
            return
        }
        viewModelScope.launch {
            _loading.value = true
            try {
                val result = todoRepository.getMaterialById(userId, topicId)
                result
                    .onSuccess { topicmaterialdata ->
                        _onetopicmaterial.value = topicmaterialdata
                    }
                    .onFailure { err ->
                        _message.value = err.message
                    }
            } catch (e: Exception) {
                _message.value = "Terjadi Kesalahan Pada Backend"
            } finally {
                _loading.value = false
            }
        }
    }

    fun getAllCourse() {
        val userId = currentUserId
        if (userId == null) {
            _message.value = "User belum login"
            return
        }
        viewModelScope.launch {
            _loading.value = true
            try {
                val result = todoRepository.getAllCourses(userId)
                result
                    .onSuccess { coursedata ->
                        _course.value = coursedata
                    }
                    .onFailure {
                        _message.value = "Terjadi Kesalahan Pada Saat Load Data"
                    }
            } catch (e: Exception) {
                _message.value = "Terjadi Kesalahan Pada Backend"
            } finally {
                _loading.value = false
            }
        }
    }

    fun getAllCourseTopicByID(courseid: Int) {
        val userId = currentUserId
        if (userId == null) {
            _message.value = "User session invalid"
            return
        }
        viewModelScope.launch {
            _loading.value = true
            try {
                val result = todoRepository.getAllTopics(userId, courseid)
                result
                    .onSuccess { coursedata ->
                        _coursedetail.value = coursedata
                    }
                    .onFailure { exception ->
                        _message.value = exception.message ?: "Terjadi Kesalahan Pada Saat Load Data"
                    }
            } catch (e: Exception) {
                _message.value = "Terjadi Kesalahan Pada Backend"
            } finally {
                _loading.value = false
            }
        }
    }

    fun getOneUserByID() {
        val userId = currentUserId
        if (userId == null) {
            _message.value = "User belum login"
            return
        }

        viewModelScope.launch {
            try {
                val result = todoRepository.getUserById(userId, userId)
                result
                    .onSuccess { user ->
                        _oneuser.value = user
                        _message.value = user.name
                    }
                    .onFailure {
                        _message.value = "User tidak ketemu"
                    }
            } catch (e: Exception) {
                _message.value = "Terjadi kesalahan"
            }
        }
    }

    fun fetchUserDetail(targetUserId: Int) {
        val adminId = currentUserId ?: return
        _loading.value = true
        viewModelScope.launch {
            try {
                val result = todoRepository.getUserById(targetUserId, adminId)
                result
                    .onSuccess { user ->
                        _oneuser.value = user
                    }
                    .onFailure { error ->
                        _message.value = error.message ?: "Failed to fetch user detail"
                    }
            } catch (e: Exception) {
                _message.value = e.message ?: "Terjadi kesalahan"
            } finally {
                _loading.value = false
            }
        }
    }

    fun insertCourse(title: String, category: String) {
        val userId = currentUserId
        if (userId == null) {
            _message.value = "User session invalid"
            return
        }

        val isDuplicate = _course.value?.any { it.title.equals(title, ignoreCase = true) } == true
        if (isDuplicate) {
            _message.value = "Judul kursus sudah digunakan, silakan cari judul lain!"
            return
        }

        viewModelScope.launch {
            _loading.value = true
            try {
                val result = todoRepository.insertCourse(userId, title, category)
                result
                    .onSuccess {
                        _message.value = "Kursus berhasil ditambahkan!"
                        _insertSuccess.value = true
                        getAllCourse()
                    }
                    .onFailure { err ->
                        _message.value = err.message ?: "Gagal menyimpan kursus"
                        _insertSuccess.value = false
                    }
            } catch (e: Exception) {
                _message.value = "Terjadi Kesalahan Pada Backend"
                _insertSuccess.value = false
            } finally {
                _loading.value = false
            }
        }
    }

    fun insertCourseTopic(
        courseId: Int,
        title: String,
        description: String,
        contentType: String,
        onSuccessRoute: () -> Unit
    ) {
        val userId = currentUserId
        if (userId == null) {
            _message.value = "User session invalid"
            return
        }
        viewModelScope.launch {
            _loading.value = true
            try {
                val result = todoRepository.insertTopic(
                    userId,
                    courseId,
                    0,
                    title,
                    description,
                    contentType
                )
                result
                    .onSuccess {topicData->
                        reset()
                        courseTopic = topicData
                        getAllCourseTopicByID(courseId)
                        _message.value = "Topic berhasil ditambahkan"
                        getAllCourseTopicByID(courseId)
                        onSuccessRoute()
                    }
                    .onFailure { err ->
                        _message.value = err.message ?: "Gagal menambahkan topic"
                    }
            } catch (e: Exception) {
                _message.value = "Terjadi Kesalahan Pada Backend"
            } finally {
                _loading.value = false
            }
        }
    }

    fun insertTopicMaterials(
        videoUrl: String,
        attachmentFile: String,
        topicId: Int,
        onSuccessRoute: () -> Unit
    ) {
        val userId = currentUserId
        if (userId == null) {
            _message.value = "User session invalid"
            return
        }
        viewModelScope.launch {
            _loading.value = true
            try {
                val result = todoRepository.insertMaterial(
                    userId,
                    videoUrl,
                    attachmentFile,
                    topicId
                )
                result
                    .onSuccess { materialData ->
                        _message.value = "Material berhasil ditambahkan"
                        materialTopic = materialData
                        getTopicMaterialByIDCourseTopic(topicId)
                        onSuccessRoute()
                    }
                    .onFailure { err ->
                        _message.value = err.message ?: "Gagal menambahkan material"
                    }
            } catch (e: Exception) {
                _message.value = "Terjadi Kesalahan Pada Backend"
            } finally {
                _loading.value = false
            }
        }
    }

    // ============================
    // ADMIN ACTIVITY LOG
    // ============================

    private val _activityLogs = MutableLiveData<List<ActivityLog>>()
    val activityLogs: LiveData<List<ActivityLog>> = _activityLogs

    fun fetchAllActivityLogs() {
        val userId = currentUserId
        if (userId == null) {
            _message.value = "User belum login"
            return
        }
        _loading.value = true

        viewModelScope.launch {
            try {
                val result = todoRepository.getAllActivityLogs(userId)
                result
                    .onSuccess { logs ->
                        _activityLogs.value = logs
                    }
                    .onFailure { error ->
                        _message.value = error.message ?: "Failed to fetch logs"
                    }
            } catch (e: Exception) {
                _message.value = e.message ?: "Terjadi kesalahan"
            } finally {
                _loading.value = false
            }
        }
    }

    // ============================
    // ADMIN PAYMENTS
    // ============================

    private val _payments = MutableLiveData<List<Payment>>()
    val payments: LiveData<List<Payment>> = _payments

    private val _paymentDetail = MutableLiveData<Payment>()
    val paymentDetail: LiveData<Payment> = _paymentDetail

    fun fetchAllPayments() {
        val userId = currentUserId
        if (userId == null) {
            _message.value = "User belum login"
            return
        }
        _loading.value = true

        viewModelScope.launch {
            try {
                val result = todoRepository.getAllPayments(userId)
                result
                    .onSuccess { list ->
                        _payments.value = list
                    }
                    .onFailure { error ->
                        _message.value = error.message ?: "Failed to fetch payments"
                    }
            } catch (e: Exception) {
                _message.value = e.message ?: "Terjadi kesalahan"
            } finally {
                _loading.value = false
            }
        }
    }

    fun fetchPaymentDetail(
        userIdParam: Int,
        paymentId: Int
    ) {
        _loading.value = true

        viewModelScope.launch {
            try {
                val result = todoRepository.getPaymentDetail(userIdParam, paymentId)
                result
                    .onSuccess { payment ->
                        _paymentDetail.value = payment
                    }
                    .onFailure { error ->
                        _message.value = error.message ?: "Failed to fetch payment detail"
                    }
            } catch (e: Exception) {
                _message.value = e.message ?: "Terjadi kesalahan"
            } finally {
                _loading.value = false
            }
        }
    }

    // ============================
    // ADMIN USERS
    // ============================

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    fun fetchAllUsers() {
        val userId = currentUserId
        if (userId == null) {
            _message.value = "User belum login"
            return
        }
        _loading.value = true

        viewModelScope.launch {
            try {
                val result = todoRepository.getAllUser(userId)
                result
                    .onSuccess { list ->
                        _users.value = list
                    }
                    .onFailure { error ->
                        _message.value = error.message ?: "Failed to fetch users"
                    }
            } catch (e: Exception) {
                _message.value = e.message ?: "Terjadi kesalahan"
            } finally {
                _loading.value = false
            }
        }
    }

    // ============================
    // CHAT / MESSAGES
    // ============================

    private val _messages = MutableLiveData<List<AdminMessage>>()
    val messages: LiveData<List<AdminMessage>> = _messages

    fun getMessagesById(userId: Int, adminId: Int) {
        viewModelScope.launch {
            try {
                val result = todoRepository.getMessagesById(userId, adminId)
                result
                    .onSuccess { list ->
                        _messages.value = list
                    }
                    .onFailure { error ->
                        _message.value = error.message ?: "Gagal mengambil pesan"
                    }
            } catch (e: Exception) {
                _message.value = e.message ?: "Terjadi kesalahan"
            }
        }
    }

    fun sendMessageFromAdmin(adminId: Int, receiverId: Int, title: String, body: String) {
        viewModelScope.launch {
            try {
                val result = todoRepository.sendMessageFromAdmin(adminId, receiverId, title, body)
                result
                    .onSuccess {
                        getMessagesById(receiverId, adminId)
                    }
                    .onFailure { error ->
                        _message.value = error.message ?: "Gagal mengirim pesan"
                    }
            } catch (e: Exception) {
                _message.value = e.message ?: "Terjadi kesalahan"
            }
        }
    }

    fun sendMessageFromUser(userId: Int, adminId: Int, title: String, body: String) {
        viewModelScope.launch {
            try {
                val result = todoRepository.sendMessageFromUser(userId, adminId, title, body)
                result
                    .onSuccess {
                        getMessagesById(userId, adminId)
                    }
                    .onFailure { error ->
                        _message.value = error.message ?: "Gagal mengirim pesan"
                    }
            } catch (e: Exception) {
                _message.value = e.message ?: "Terjadi kesalahan"
            }
        }
    }
    // ============================
// CHATBOT
// ============================

    private val _chatbotMessages = MutableLiveData<List<CsChatbotChat>>()
    val chatbotMessages: LiveData<List<CsChatbotChat>> = _chatbotMessages

    fun getChatbotMessages(userId: Int) {
        viewModelScope.launch {
            try {
                val result = todoRepository.getChats(userId)

                result
                    .onSuccess { chats ->
                        _chatbotMessages.value = chats
                    }
                    .onFailure { error ->
                        _message.value = error.message ?: "Failed to load chatbot messages"
                    }

            } catch (e: Exception) {
                _message.value = e.message ?: "Terjadi kesalahan"
            }
        }
    }

    fun sendAiMessage(
        userId: Int,
        role: String,
        message: String
    ) {
        viewModelScope.launch {
            try {
                // 1. Simpan prompt user ke DB
                todoRepository.createChat(
                    userId = userId,
                    sender = "user",
                    message = message
                )
                
                // Refresh UI agar prompt muncul
                getChatbotMessages(userId)

                // 2. Ambil respon AI dari backend
                // Kita gunakan userId.toString() sebagai identifier role di backend agar riwayat unik per user
                val aiResult = todoRepository.chatWithAi(userId.toString(), message)

                if (aiResult.isSuccess) {
                    val aiData = aiResult.getOrNull()
                    if (aiData != null) {
                        // 3. Simpan balasan AI ke DB dengan sender "ai"
                        // Respon AI ada di aiData.data (sesuai backend response.text)
                        todoRepository.createChat(
                            userId = userId,
                            sender = "bot",
                            message = aiData.data
                        )
                        // 4. Reload percakapan
                        getChatbotMessages(userId)
                    }
                } else {
                    _message.value = "AI Error: ${aiResult.exceptionOrNull()?.message}"
                }

            } catch (e: Exception) {
                _message.value = "Chat Error: ${e.message}"
            }
        }
    }


    fun reset() {
        _message.value = ""
        _loading.value = false
        _activityLogs.value = emptyList()
        _payments.value = emptyList()
        _users.value = emptyList()
        _messages.value = emptyList()
        _chatbotMessages.value = emptyList()
        _oneuser.value = null
        _onetopicmaterial.value = null
        _insertSuccess.value = false
    }
}

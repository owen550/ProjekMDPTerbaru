package com.example.fe.ui.todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fe.data.ActivityLog
import com.example.fe.data.AdminMessage
import com.example.fe.data.Payment
import com.example.fe.data.Course
import com.example.fe.data.User
import com.example.fe.data.repositories.TodoRepository
import com.example.fe.ui.todoform.currentUserId
import com.example.fe.user
import kotlinx.coroutines.launch

class TodosViewModel(
    private val todoRepository: TodoRepository
): ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading


    // === get data ====
    private val _oneuser = MutableLiveData<User>()
    var oneuser: LiveData<User> = _oneuser

    private val _course = MutableLiveData<List<Course>>()
    var course: LiveData<List<Course>> = _course


    fun getAllCourse() {
        val userId = currentUserId
        if (userId == null) {
            _message.value = "User belum login"
            return
        }
        viewModelScope.launch {
            _loading.value = true
            try {
                // Gunakan !! untuk memastikan Int dilempar ke repository
                val result = todoRepository.getAllCourses(userId!!)
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

    fun getOneUserByID() {
        val userId = currentUserId
        if (userId == null) {
            _message.value = "User belum login"
            return
        }

        viewModelScope.launch {
            try {
                val result = todoRepository.getUserById(userId!!, userId!!)
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
                val result = todoRepository.getUserById(targetUserId, adminId!!)
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
                val result = todoRepository.getAllActivityLogs(userId!!)
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
                val result = todoRepository.getAllPayments(userId!!)
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
                val result = todoRepository.getAllUser(userId!!)
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


    fun reset() {
        _message.value = ""
        _loading.value = false
        _activityLogs.value = emptyList()
        _payments.value = emptyList()
        _users.value = emptyList()
        _oneuser.value = null
        _messages.value = emptyList()
    }
}

package com.example.fe.ui.todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fe.data.ActivityLog
import com.example.fe.data.User
import com.example.fe.data.repositories.TodoRepository
import com.example.fe.ui.todoform.currentUserId
import kotlinx.coroutines.launch

class TodosViewModel(
    private val todoRepository: TodoRepository
): ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _oneuser = MutableLiveData<User>()
    val oneuser: LiveData<User> = _oneuser


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
                        _message.value =
                            error.message ?: "Failed to fetch logs"
                    }

            } catch (e: Exception) {

                _message.value =
                    e.message ?: "Terjadi kesalahan"

            } finally {

                _loading.value = false

            }
        }
    }


    fun reset() {
        _message.value = ""
        _loading.value = false
        _activityLogs.value = emptyList()
    }
}
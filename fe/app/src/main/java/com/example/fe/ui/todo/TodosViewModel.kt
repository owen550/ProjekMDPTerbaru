package com.example.fe.ui.todo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fe.data.User
import com.example.fe.data.repositories.TodoRepository
import kotlinx.coroutines.launch

class TodosViewModel(
    private val todoRepository: TodoRepository
): ViewModel() {

    // === variabel ===
    private val _message = MutableLiveData<String>();
    var message: LiveData<String> = _message

    private val _loading = MutableLiveData<Boolean>();
    var loading: LiveData<Boolean> = _loading

    // === ini cuma buat tes ====
    private val _oneuser = MutableLiveData<User>();
    var oneuser: LiveData<User> = _oneuser

    private val _course = MutableLiveData<User>();
    var course: LiveData<User> = _course



    fun getOneUserByID() {
        viewModelScope.launch {
            try {
                val result = todoRepository.getUserById(6, 3)

                result
                    .onSuccess { user ->
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

    fun getOneCourseByID() {
        viewModelScope.launch {
            try {
                val result = todoRepository.getUserById(6, 3)

                result
                    .onSuccess { user ->
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

}
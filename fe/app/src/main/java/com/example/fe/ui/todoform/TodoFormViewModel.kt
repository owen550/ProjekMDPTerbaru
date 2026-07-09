package com.example.fe.ui.todoform

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fe.data.User
import com.example.fe.data.repositories.TodoRepository
import com.example.fe.user
import kotlinx.coroutines.launch

class TodoFormViewModel(
    private val todoRepository: TodoRepository
): ViewModel() {

    // === variabel ===
    private val _message = MutableLiveData<String>();
    var message: LiveData<String> = _message

    private val _loading = MutableLiveData<Boolean>(false);
    var loading: LiveData<Boolean> = _loading

    private val _login = MutableLiveData<Boolean>(false);
    var login: LiveData<Boolean> = _login

    // === all function ===
    fun doLogin(usernameoremail:String,password:String){
        _loading.value = true;
        viewModelScope.launch {
            try {
                val result = todoRepository.doLogin(usernameoremail,password)
                result
                    .onSuccess { userdata ->
                        user = userdata
                        _login.value = true
                    }
                    .onFailure {
                        _message.value = "Username/Email atau Password Salah !!!"
                    }
            }
            catch (e: Exception){
                _message.value = "Terjadi Kesalahan Pada Backend"
            }
            finally {
                _loading.value = false
            }
        }
    }

    fun register(
         name:String,
         username:String,
         email:String,
         password:String,
         birthdayDate:String
    ){
        // ==== buat user baru ====
        var UserBaru = User(
            id = 0,

            name = name,
            username = username,
            email = email,
            password = password,
            birthday_date = birthdayDate,

            role = "student",
            tier = "free",
            google_id = "",
            created_at = "",
            updated_at = "",
            deleted_at = "",
            points = 0
        )
        _loading.value = true;
        viewModelScope.launch {
            try {
                val result = todoRepository.addUser(UserBaru);
                result
                    .onSuccess{ userdata ->
                        user = userdata
                        _message.value = "User berhasil register"
                    }
                    .onFailure {
                            error ->
                        _message.value = error.message ?: "Gagal register, terjadi kesalahan"
                    }
            }
            catch (e : Exception){
                _message.value = "Terjadi Kesalahan Pada Backend"
            }
            finally {
                _loading.value = false
            }
        }

    }

    fun reset(){
        _message.value = ""
        _login.value = false
        _loading.value = false
    }

}
package com.example.fe.ui.todoform

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fe.data.Course
import com.example.fe.data.User
import com.example.fe.data.repositories.TodoRepository
import com.example.fe.data.source.local.UserEntity
import com.example.fe.statusUser
import com.example.fe.user
import kotlinx.coroutines.launch

var currentUserId: Int? = null

class TodoFormViewModel(
    private val todoRepository: TodoRepository
): ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _loading = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean> = _loading

    private val _login = MutableLiveData<Boolean>(false)
    val login: LiveData<Boolean> = _login

    private val _userlokal = MutableLiveData<User>()
    val userlokal: LiveData<User> = _userlokal

    fun updateUser(
        name: String,
        username: String,
    ){
        _loading.value = true
        viewModelScope.launch {
            try {
                val updatedUsers = user?.copy(
                    username = username,
                    name = name
                )
                var result = todoRepository.updateUser(updatedUsers!!)
                    .onSuccess {
                        user = updatedUsers // ganti update usernya
                        _message.value = "Berhasil Mengupate User"
                    }
                    .onFailure { error ->
                        _message.value = "Gagal : " + error.message
                    }
            } catch (e: Exception){
                _message.value = "" // jangan munculin apa apa
            }
            finally {
                _loading.value = false
            }
        }
    }
    fun getUserTerakhir(){
        _loading.value = true
        viewModelScope.launch {
            try {
                val result = todoRepository.getLastUserDESC()
                if(result != null){ // kalau ngak kosonh
                    _userlokal.value = result
                }
            } catch (e: Exception){
                _message.value = "" // jangan munculin apa apa
            }
            finally {
                _loading.value = false
            }
        }
    }

    fun doLogin(usernameoremail:String, password:String){
        _loading.value = true
        viewModelScope.launch {
            try {
                val result = todoRepository.doLogin(usernameoremail,password)

                result
                    .onSuccess { userdata ->
                        // lakukan login
                        user = userdata
                        currentUserId = userdata.id
                        _login.value = true
                    }
                    .onFailure {
                        _message.value = "Username/Email atau Password Salah !!!"
                    }
            } catch (e: Exception){
                _message.value = "Terjadi Kesalahan Pada Backend"
            }
            finally {
                _loading.value = false
            }
        }
    }

    fun cekStatusPremiFreeUser(){
        _loading.value = true
        viewModelScope.launch {
            try {
                val result = todoRepository.cekStatusFreePre(user!!.id!!.toInt())
                result
                    .onSuccess { userdata ->
                        statusUser = userdata // kalau false berarti free, kalau true berarti premi
                    }
                    .onFailure {
                        statusUser = false
                    }
            } catch (e: Exception){
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

        val userBaru = User(
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

        _loading.value = true

        viewModelScope.launch {
            try {

                val result = todoRepository.addUser(userBaru)

                result
                    .onSuccess { userdata ->
                        user = userdata
                        _message.value = "User berhasil register"
                    }
                    .onFailure { error ->
                        _message.value = error.message ?: "Gagal register"
                    }

            } catch(e:Exception){
                _message.value = e.message
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
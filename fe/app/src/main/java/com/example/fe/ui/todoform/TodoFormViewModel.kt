package com.example.fe.ui.todoform

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fe.data.repositories.TodoRepository

class TodoFormViewModel(private val todoRepository: TodoRepository): ViewModel() {

    // === variabel ===
    private val _message = MutableLiveData<String>();
    var message: LiveData<String> = _message
}
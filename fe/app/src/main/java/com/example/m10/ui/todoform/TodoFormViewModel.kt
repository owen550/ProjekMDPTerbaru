package com.example.m10.ui.todoform

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m10.data.repositories.TodoRepository
import kotlinx.coroutines.launch

class TodoFormViewModel(private val todoRepository: TodoRepository): ViewModel() {
    private var id = ""
    private val _content = MutableLiveData<String>()
    private val _state = MutableLiveData("idle")
    private var completed = false
    val content: LiveData<String>
        get() = _content
    val state: LiveData<String>
        get() = _state
    fun setTodo(newId:String) {
        viewModelScope.launch {
            val todo = todoRepository.getById(newId)
            if (todo!=null) {
                id = newId
                _content.value = todo.content
                completed = todo.completed
            } else {
                throw Exception("Todo not found")
            }
        }
    }
    fun putTodo(id:String, content:String) {
        viewModelScope.launch {
            _state.value = "processing"
            if (id=="") {
                todoRepository.insert(content)
            }
            else {
                todoRepository.update(id, content, completed)
            }
            _state.value = "done"
        }
    }
}
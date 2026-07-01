package com.example.m10.ui.todos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.m10.data.Todo
import com.example.m10.data.repositories.TodoRepository
import kotlinx.coroutines.launch

class TodosViewModel(private val todoRepository: TodoRepository): ViewModel() {
    private var filter:String = "Pending"
    private val _todoList = ArrayList<Todo>()
    private val _todos = MutableLiveData(_todoList.toList())
    val todos: LiveData<List<Todo>>
        get() = _todos
    fun setFilter(filter:String) {
        this.filter = filter
        _todos.value = _todoList.filter {
            if (filter == "Pending") {
                it.completed == false
            }
            else {
                it.completed == true
            }
        }
    }
    fun init() {
        viewModelScope.launch {
            refreshList()
        }
    }
    private suspend fun refreshList() {
        _todoList.clear()
        _todoList.addAll(todoRepository.getAll())
        setFilter(filter)
    }
    fun deleteTodo(id:String) {
        viewModelScope.launch {
            todoRepository.delete(id)
            refreshList()
        }
    }
    fun toggleCompleted(id:String, completed:Boolean) {
        viewModelScope.launch {
            val todo = todoRepository.getById(id)
            if (todo!=null) {
                todoRepository.update(id,todo.content,completed)
                refreshList()
            }
        }
    }
    fun sync(){
        viewModelScope.launch {
            todoRepository.sync()
            refreshList()
        }
    }
}
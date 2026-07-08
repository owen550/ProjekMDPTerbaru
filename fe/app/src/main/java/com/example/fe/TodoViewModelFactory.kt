package com.example.fe

import com.example.fe.ui.todo.TodosViewModel
import com.example.fe.ui.todoform.TodoFormViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras

val TodoViewModelFactory=object: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras
    ): T =
        with(modelClass) {
            val application = checkNotNull(extras[APPLICATION_KEY]) as TodoApplication
            val todoRepository = application.todoRepository
            when {
                isAssignableFrom(TodosViewModel::class.java) -> TodosViewModel(todoRepository)
                isAssignableFrom(TodoFormViewModel::class.java) -> TodoFormViewModel(todoRepository)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class" + ": ${modelClass.name}")
            }
        } as T
}
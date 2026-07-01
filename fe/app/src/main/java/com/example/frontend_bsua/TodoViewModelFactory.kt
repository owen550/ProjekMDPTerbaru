package com.example.frontend_bsua

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.frontend_bsua.ui.todoform.AddJadwalViewModel
import com.example.frontend_bsua.ui.todos.ShowFragmenViewModel

val TodoViewModelFactory=object : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras
    ): T =
        with(modelClass) {
            val application = checkNotNull(extras[APPLICATION_KEY]) as TodoApplications
            val todoRepository = application.todoRepository
            when {
                isAssignableFrom(ShowFragmenViewModel::class.java) -> ShowFragmenViewModel(todoRepository)
                isAssignableFrom(AddJadwalViewModel::class.java) -> AddJadwalViewModel(todoRepository)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class" + ": ${modelClass.name}")
            }
        } as T
}


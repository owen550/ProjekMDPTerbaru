package com.example.m10.ui.todos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.m10.MainDispatcherRule
import com.example.m10.data.Todo
import com.example.m10.data.repositories.TodoRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TodosViewModelTest {
    lateinit var todosViewModel: TodosViewModel
    lateinit var todoRepository: TodoRepository
    lateinit var mockDataSource: MutableList<Todo>
    // Rule hampir sama dengan @Before + @After. Biasanya rule dipakai
    // untuk reuse code yang ada di dalam @Before + @After di beberapa
    // class test berbeda
    @get:Rule
    val rule = InstantTaskExecutorRule()
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        mockDataSource = mutableListOf(
            Todo.create("Play new game").apply { completed = true },
            Todo.create("Meet up with friends").apply { completed = true },
            Todo.create("Study for test"),
            Todo.create("Buy milk"),
            Todo.create("Do homework")
        )
        todoRepository = mockk()
        coEvery { todoRepository.getAll() } returns mockDataSource.toList()
        todosViewModel = TodosViewModel(todoRepository)
        todosViewModel.init()
    }
    @After
    fun tearDown() { }

    @Test
    fun init() {
        val todos = todosViewModel.todos.value!!
        val filteredTodos = mockDataSource.filter { !it.completed }
        val completedTodo = todos.find { it.completed }
        assertNull("When first initialized, ViewModel must only display " +
                "uncompleted todos", completedTodo)
        assertTrue("Todo ArrayList must have the same size",
            todos.size == filteredTodos.size)
        for(i in todos.indices){
            val t0 = todos[i]
            val t1 = filteredTodos[i]
            if(t0.id != t1.id || t0.content != t1.content ||
                t0.completed != t1.completed){
                fail("different todo at index $i")
            }
        }
        coVerify { todoRepository.getAll() }
    }

    @Test
    fun setFilter() {
        todosViewModel.setFilter("Completed")
        var todos = todosViewModel.todos.value!!
        assertNull("Must only display completed todos",
            todos.find { !it.completed })
        todosViewModel.setFilter("Pending")
        todos = todosViewModel.todos.value!!
        assertNull("Must only display uncompleted todos",
            todos.find { it.completed })
    }

    @Test
    fun toggleCompleted() {
        val todo = mockDataSource[0]
        val idSlot0 = slot<String>()
        val idSlot1 = slot<String>()
        val contentSlot = slot<String>()
        val completedSlot = slot<Boolean>()

        coEvery { todoRepository.getById(capture(idSlot0)) } answers {
            mockDataSource.find { it.id == idSlot0.captured }
        }
        coEvery { todoRepository.update(capture(idSlot1),
            capture(contentSlot), capture(completedSlot)) } answers {
            mockDataSource.find { it.id == idSlot1.captured }!!.apply {
                content = contentSlot.captured
                completed = completedSlot.captured
            }
        }

        todosViewModel.toggleCompleted(todo.id, false)
        coVerify { todoRepository.update(todo.id, todo.content, false) }
        assertFalse(todo.completed)
        todosViewModel.toggleCompleted(todo.id, true)
        coVerify { todoRepository.update(todo.id, todo.content, true) }
        assertTrue(todo.completed)
    }
}

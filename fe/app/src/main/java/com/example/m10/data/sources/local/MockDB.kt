package com.example.m10.data.sources.local

import com.example.m10.data.Todo
import java.util.Date

object MockDB: LocalDataSource {
    val todos = mutableListOf<Todo>()

    init {
        todos.add(Todo.create("Todo 1"))
        todos.add(Todo.create("Todo 2"))
        todos.add(Todo.create("Todo 3"))
        todos.add(Todo.create("Todo 4"))
        todos.add(Todo.create("Todo 5"))
    }

    override suspend fun getAll(): List<Todo> {
        return todos.filter { it.deletedAt == null }.map { it.copy() }
    }

    override suspend fun getById(id: String): Todo? {
        return todos.find { it.id == id && it.deletedAt == null }?.copy()
    }

    override suspend fun insert(content: String): Todo {
        val todo = Todo.create(content)
        todos.add(todo)
        return todo.copy()
    }

    override suspend fun update(
        id: String,
        content: String,
        completed: Boolean
    ): Todo {
        val todo = todos.find { it.id == id && it.deletedAt == null }
        if(todo == null){
            throw Exception("Todo not found")
        }
        todo.content = content
        todo.completed = completed
        todo.updatedAt = Date()
        return todo.copy()
    }

    override suspend fun delete(id: String): Todo {
        val todo = todos.find { it.id == id && it.deletedAt == null }
        if(todo == null){
            throw Exception("Todo not found")
        }
        todo.deletedAt = Date()
        return todo.copy()
    }

    override suspend fun getUnsynced(): List<Todo> {
        TODO("Not yet implemented")
    }

    override suspend fun sync(todos: List<Todo>) {
        TODO("Not yet implemented")
    }
}

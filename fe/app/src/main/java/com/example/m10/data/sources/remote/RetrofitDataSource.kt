package com.example.m10.data.sources.remote

import com.example.m10.data.Todo
import java.util.Date

fun Todo.toTodoJson(): TodoJson{
    return TodoJson(id, content, completed, createdAt.time, updatedAt.time, deletedAt?.time)
}

fun TodoJson.toTodo(): Todo{
    return Todo(id, content, completed, Date(createdAt), Date(updatedAt), deletedAt?.let{Date(it)})
}

class RetrofitDataSource(private val retrofitService: WebService): RemoteDataSource {
    override suspend fun put(todo: Todo): Todo {
        val request = todo.toTodoJson()
        val response = retrofitService.putTodo(todo.id, request)
        return response.toTodo()
    }

    override suspend fun sync(todos: List<Todo>): List<Todo> {
        return retrofitService.syncTodo(todos.map { it.toTodoJson()}).map { it.toTodo() }
    }
}
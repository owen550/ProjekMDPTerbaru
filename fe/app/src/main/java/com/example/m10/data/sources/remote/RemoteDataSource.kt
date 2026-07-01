package com.example.m10.data.sources.remote

import com.example.m10.data.Todo

interface RemoteDataSource {
    suspend fun put(todo: Todo) : Todo
    suspend fun sync(todos: List<Todo>) : List<Todo>
}
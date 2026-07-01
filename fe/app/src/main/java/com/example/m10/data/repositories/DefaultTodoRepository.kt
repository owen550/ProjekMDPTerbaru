package com.example.m10.data.repositories

import com.example.m10.data.Todo
import com.example.m10.data.sources.local.LocalDataSource
import com.example.m10.data.sources.remote.RemoteDataSource

class DefaultTodoRepository(val localDataSource: LocalDataSource, val remoteDataSource: RemoteDataSource): TodoRepository {
    override suspend fun getAll(): List<Todo> {
        return localDataSource.getAll()
    }

    override suspend fun getById(id: String): Todo? {
        return localDataSource.getById(id)
    }

    override suspend fun insert(content: String): Todo {
        val todo = localDataSource.insert(content)
        remoteDataSource.put(todo)
        return todo
    }

    override suspend fun update(
        id: String,
        content: String,
        completed: Boolean
    ): Todo {
        val todo = localDataSource.update(id,content,completed)
        remoteDataSource.put(todo)
        return todo
    }

    override suspend fun delete(id: String): Todo {
        val todo = localDataSource.delete(id)
        remoteDataSource.put(todo)
        return todo
    }

    override suspend fun sync() {
        val clientTodos = localDataSource.getUnsynced()
        val serverTodos = remoteDataSource.sync(clientTodos)
        localDataSource.sync(serverTodos)
    }
}
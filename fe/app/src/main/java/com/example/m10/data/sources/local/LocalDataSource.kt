package com.example.m10.data.sources.local

import com.example.m10.data.Todo

interface LocalDataSource {
    suspend fun getAll():List<Todo>
    suspend fun getById(id:String):Todo?
    suspend fun insert(content:String): Todo
    suspend fun update(id:String,content:String,completed:Boolean): Todo
    suspend fun delete(id:String): Todo
    suspend fun getUnsynced(): List<Todo>
    suspend fun sync(todos: List<Todo>)
}
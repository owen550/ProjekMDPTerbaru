package com.example.m10.data.sources.remote

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface WebService {
    @PUT("/todos/{id}")
    suspend fun putTodo(@Path("id") id:String, @Body todo: TodoJson): TodoJson
    @POST("todos/sync")
    suspend fun syncTodo(@Body body: List<TodoJson>): List<TodoJson>
}
package com.example.m10

import android.app.Application
import com.example.m10.data.repositories.DefaultTodoRepository
import com.example.m10.data.repositories.TodoRepository
import com.example.m10.data.sources.local.AppDatabase
import com.example.m10.data.sources.local.RoomDataSource
import com.example.m10.data.sources.remote.RetrofitDataSource
import com.example.m10.data.sources.remote.WebService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class TodoApplication: Application(){
    lateinit var todoRepository: TodoRepository
    override fun onCreate() {
        super.onCreate()
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val retrofit = Retrofit.Builder().addConverterFactory(
            MoshiConverterFactory.create(moshi)
        ).baseUrl("http://192.168.103.71:3000").build()
        val retrofitService = retrofit.create(WebService::class.java)
        todoRepository = DefaultTodoRepository(RoomDataSource(AppDatabase.getInstance(baseContext)),
            RetrofitDataSource(retrofitService))
    }
}

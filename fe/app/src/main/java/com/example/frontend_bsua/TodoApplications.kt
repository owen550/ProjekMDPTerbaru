package com.example.frontend_bsua

import android.app.Application
import com.example.frontend_bsua.data.repositories.DefaultTodoRepository
import com.example.frontend_bsua.data.repositories.TodoRepository
import com.example.frontend_bsua.data.sources.local.AppDatabase
import com.example.frontend_bsua.data.sources.local.RoomDataSource
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class TodoApplications: Application() {
    lateinit var todoRepository: TodoRepository
    override fun onCreate() {
        super.onCreate()
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val retrofit = Retrofit.Builder().addConverterFactory(
            MoshiConverterFactory.create(moshi)
        ).baseUrl("http://192.168.103.71:3000").build() // ubah ip nya nanti

//        val retrofitService = retrofit.create(WebService::class.java)

        todoRepository = DefaultTodoRepository(RoomDataSource(AppDatabase.getDatabase(baseContext)))
//        todoRepository = DefaultTodoRepository(RoomDataSource(AppDatabase.getDatabase(baseContext)),
//            RetrofitDataSource(retrofitService))
    }
}


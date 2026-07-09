package com.example.fe

import android.app.Application
import com.example.fe.data.repositories.DefaultTodoRepository
import com.example.fe.data.repositories.TodoRepository
import com.example.fe.data.source.local.RoomDataSource
import com.example.fe.data.source.remote.RetrofitDataSource
import com.example.fe.data.source.remote.WebService
import com.example.frontend_bsua.data.sources.local.AppDatabase
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

        ).baseUrl("http://192.168.1.39:3000/").build()//  10.10.5.202 || 192.168.110.9

        val retrofitService = retrofit.create(WebService::class.java)
        todoRepository = DefaultTodoRepository( // ntik lek wes nyalakno lagi room me
            RoomDataSource(AppDatabase.getDatabase(baseContext)),
            RetrofitDataSource(retrofitService))
    }
}

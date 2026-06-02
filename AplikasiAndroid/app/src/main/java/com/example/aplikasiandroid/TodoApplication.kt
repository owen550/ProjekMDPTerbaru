package com.example.aplikasiandroid

import android.app.Application

class TodoApplication: Application() {
    val urlipv4 = "192.168.110.9" // ubah sesuai ip config

//    lateinit var todoRepository: TodoRepository
//    override fun onCreate() {
//        super.onCreate()
//        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
//        val retrofit = Retrofit.Builder().addConverterFactory(
//            MoshiConverterFactory.create(moshi)
//        ).baseUrl("http://${urlipv4}:3000/").build()
//        //10.0.2.2:3000 kalau emulator
//        val retrofitService = retrofit.create(WebService::class.java)
//
//        todoRepository = DefaultTodoRepository(RoomDataSource(AppDatabase.getInstance(baseContext)),
//            RetrofitDataSource(retrofitService))
//    }
}
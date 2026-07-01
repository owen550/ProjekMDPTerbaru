package com.example.m10.data.sources.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TodoEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun todoDao(): TodoDao
    companion object{
        private var INSTANCE: AppDatabase?=null
        fun getInstance(context: Context): AppDatabase{
            return INSTANCE?:synchronized(this){
                INSTANCE?:Room.databaseBuilder(context, AppDatabase::class.java, "madmdpm12")
                    .fallbackToDestructiveMigration(true)
                    .build()
                    .also { INSTANCE=it }
            }
        }
    }
}
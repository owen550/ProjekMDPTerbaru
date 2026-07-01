package com.example.m10.data.sources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.m10.data.Todo

@Dao
interface TodoDao {
    @Query("Select * from todos where deletedAt is null order by createdAt desc")
    suspend fun getAll():List<TodoEntity>
    @Query("Select * from todos where id=:id and deletedAt is null")
    suspend fun getById(id:String): TodoEntity?
    @Insert
    suspend fun insert(todo: TodoEntity)
    @Update
    suspend fun update(todo: TodoEntity)
    @Query("delete from todos")
    suspend fun deleteAll()
    @Insert
    suspend fun bulkInsert(todos: List<TodoEntity>)
    @Query("select * from todos")
    suspend fun getUnsynced(): List<TodoEntity>
}
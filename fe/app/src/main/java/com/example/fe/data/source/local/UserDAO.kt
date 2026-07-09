package com.example.fe.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface UserDao {
    // semua user
    @Query("SELECT * FROM User") // nama tabele user, jok diganti !!!
    suspend fun getAll(): List<UserEntity>
    // ambil sesuai id user
    @Query("SELECT * FROM User WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): UserEntity?
    // ambil 1 user terakhir (ini buat login ntik)
    @Query("SELECT * FROM User ORDER BY id DESC LIMIT 1")
    suspend fun getLastUserDESC(): UserEntity?
    // insert room
    @Insert()
    suspend fun insert(user: UserEntity): Long
    // update room
    @Update
    suspend fun update(user: UserEntity): Int
    // delete room
    @Delete
    suspend fun delete(user: UserEntity): Int
}
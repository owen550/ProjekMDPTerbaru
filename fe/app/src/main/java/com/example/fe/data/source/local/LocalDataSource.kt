package com.example.fe.data.source.local

import com.example.fe.data.User

interface LocalDataSource {
    suspend fun getAll(): List<User>
    suspend fun getById(id: Int): User?
    suspend fun getLastUserDESC(): User?
    suspend fun insert(jadwal: User)
    suspend fun update(jadwal: User)
    suspend fun delete(jadwal: User)
}
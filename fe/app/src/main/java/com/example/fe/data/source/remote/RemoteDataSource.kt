package com.example.fe.data.remote

import com.example.fe.data.User
import retrofit2.http.Field

interface RemoteDataSource {

    // =======================
    // User
    // =======================

    suspend fun getAllUser(
        userId: Int
    ): Result<List<User>>

    suspend fun addUser(
        user: User
    ): Result<User>

    suspend fun getUserById(
        userId: Int,
        userPencariId: Int
    ): Result<User>

    suspend fun updateUser(
        user: User
    ): Result<User>

    suspend fun deleteUser(
        userId: Int
    ): Result<User>

    suspend fun doLogin(
        usernameoremail: String,
        password: String
    ): Result<User>
}
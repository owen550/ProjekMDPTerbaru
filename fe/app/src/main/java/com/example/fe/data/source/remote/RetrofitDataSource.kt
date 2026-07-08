package com.example.fe.data.source.remote

import com.example.fe.data.User
import com.example.fe.data.remote.RemoteDataSource

class RetrofitDataSource(
    private val retrofitService: WebService
) : RemoteDataSource {

    override suspend fun getAllUser(userId: Int): Result<List<User>> {
        return try {
            val response = retrofitService.getAllUser(userId)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addUser(user: User): Result<User> {
        return try {
            val response = retrofitService.addUser(
                name = user.name,
                username = user.username,
                password = user.password,
                email = user.email,
                googleId = user.google_id,
                birthdayDate = user.birthday_date,
                role = user.role,
                tier = user.tier
            )

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUserById(
        userId: Int,
        userPencariId: Int
    ): Result<User> {
        return try {
            val response = retrofitService.getUserById(userId, userPencariId)

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateUser(user: User): Result<User> {
        return try {
            val response = retrofitService.updateUser(
                userId = user.id!!,
                name = user.name,
                username = user.username,
                password = user.password,
                email = user.email,
                googleId = user.google_id,
                birthdayDate = user.birthday_date,
                role = user.role,
                tier = user.tier
            )

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteUser(userId: Int): Result<User> {
        return try {
            val response = retrofitService.deleteUser(userId)

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun doLogin(
        usernameoremail: String,
        password: String
    ): Result<User> {
        return try {
            val response = retrofitService.doLogin(usernameoremail,password)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
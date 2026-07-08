package com.example.fe.data.repositories

import com.example.fe.data.User
import com.example.fe.data.remote.RemoteDataSource

class DefaultTodoRepository(
//    val localDataSource: LocalDataSource,
    val remoteDataSource: RemoteDataSource
) : TodoRepository {

    override suspend fun getAllUser(userId: Int): Result<List<User>> {
        return remoteDataSource.getAllUser(userId)
    }

    override suspend fun addUser(user: User): Result<User> {
        return remoteDataSource.addUser(user)
    }

    override suspend fun getUserById(
        userId: Int,
        userPencariId: Int
    ): Result<User> {
        return remoteDataSource.getUserById(userId, userPencariId)
    }

    override suspend fun updateUser(user: User): Result<User> {
        return remoteDataSource.updateUser(user)
    }

    override suspend fun deleteUser(userId: Int): Result<User> {
        return remoteDataSource.deleteUser(userId)
    }
}
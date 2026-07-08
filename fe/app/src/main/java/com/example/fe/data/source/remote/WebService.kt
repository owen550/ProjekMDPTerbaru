package com.example.fe.data.source.remote

import com.example.fe.data.User
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.PUT

interface WebService {

    // =======================
    // User
    // =======================

    @FormUrlEncoded
    @POST("api/users/alldata")
    suspend fun getAllUser(
        @Field("userid") userId: Int
    ): Response<List<User>>

    @FormUrlEncoded
    @POST("api/users/addusers")
    suspend fun addUser(
        @Field("name") name: String,
        @Field("username") username: String?,
        @Field("password_user") password: String?,
        @Field("email") email: String,
        @Field("google_id") googleId: String?,
        @Field("birthday_date") birthdayDate: String?,
        @Field("role") role: String,
        @Field("tier") tier: String?
    ): Response<User>

    @FormUrlEncoded
    @POST("api/users/getdatabyid")
    suspend fun getUserById(
        @Field("userid") userId: Int,
        @Field("userpencari") userPencariId: Int
    ): Response<User>

    @FormUrlEncoded
    @PUT("api/users/updateuser")
    suspend fun updateUser(
        @Field("userid") userId: Int,
        @Field("name") name: String,
        @Field("username") username: String?,
        @Field("password_user") password: String?,
        @Field("email") email: String,
        @Field("google_id") googleId: String?,
        @Field("birthday_date") birthdayDate: String?,
        @Field("role") role: String,
        @Field("tier") tier: String?
    ): Response<User>

    @FormUrlEncoded
    @PUT("api/users/deleteuser")
    suspend fun deleteUser(
        @Field("userid") userId: Int
    ): Response<User>
}
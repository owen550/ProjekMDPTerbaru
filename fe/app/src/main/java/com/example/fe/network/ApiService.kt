package com.example.fe.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("auth/google")
    suspend fun loginWithGoogle(@Body request: LoginRequest): Response<LoginResponse>
}

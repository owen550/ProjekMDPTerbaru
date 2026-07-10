package com.example.fe.network

data class LoginRequest(
    val idToken: String
)

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val token: String?,
    val user: User?
)

data class User(
    val id: String,
    val email: String,
    val name: String
)

package com.example.fe.network

data class RegisterRequest(
    val name: String,
    val email: String,
    val username: String,
    val password: String,
    val birthday: String
)

data class RegisterResponse(
    val success: Boolean,
    val message: String,
    val user: User?
)

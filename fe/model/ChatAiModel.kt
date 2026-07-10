package com.example.fe.model

data class ChatAiRequest(
    val role: String,
    val pesan: String
)

data class ChatAiResponse(
    val success: Boolean,
    val message: String,
    val data: String? = null,
    val error: String? = null
)

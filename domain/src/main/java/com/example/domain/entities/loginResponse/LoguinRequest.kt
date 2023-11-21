package com.example.domain.entities.loginResponse

data class LoginRequest(
    val client_id: String,
    val client_secret: String,
    val email: String,
    val grant_type: String,
    val password: String
)
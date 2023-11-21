package com.example.domain.entities.loginResponse

data class LoginResponse(
    val access_token: String? = null,
    val created_at: Long? = null,
    val expires_in: Long? = null,
    val refresh_token: String? = null,
    val token_type: String? = null,
    val error_message: String? = null
)
package com.example.nimble.domain.entities.refreshToken

data class RefreshRequest(
    val client_id: String,
    val client_secret: String,
    val grant_type: String,
    val refresh_token: String
)
package com.example.data.responseObjects.loginResponse

import com.example.domain.entities.loginResponse.LoginResponse

data class DataDto(
    val attributes: LoginResponse,
    val id: String,
    val type: String
)
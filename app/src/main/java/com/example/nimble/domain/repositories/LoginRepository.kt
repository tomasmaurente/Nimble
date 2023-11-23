package com.example.nimble.domain.repositories

import com.example.nimble.domain.entities.loginResponse.LoginRequest
import com.example.nimble.domain.entities.loginResponse.LoginResponse

interface LoginRepository {
    suspend fun loginRequest(loginParameters: LoginRequest): LoginResponse
}
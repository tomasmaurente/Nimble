package com.example.domain.repositories

import com.example.domain.entities.Result
import com.example.domain.entities.loginResponse.LoginRequest
import com.example.domain.entities.loginResponse.LoginResponse

interface LoginRepository {
    suspend fun loginRequest(loginParameters: LoginRequest): LoginResponse
}
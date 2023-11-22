package com.example.domain.repositories

import com.example.domain.entities.Result
import com.example.domain.entities.loginResponse.LoginResponse
import com.example.domain.entities.refreshToken.RefreshRequest

interface RefreshTokenRepository {
    suspend fun refreshTokenRequest(refreshRequest: RefreshRequest): Result<LoginResponse>
}
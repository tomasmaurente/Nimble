package com.example.nimble.domain.repositories

import com.example.nimble.domain.entities.loginResponse.LoginResponse
import com.example.nimble.domain.entities.refreshToken.RefreshRequest
import com.example.nimble.domain.entities.Result


interface RefreshTokenRepository {
    suspend fun refreshTokenRequest(refreshRequest: RefreshRequest): Result<LoginResponse>
}
package com.example.domain.usecases

import com.example.domain.entities.Result
import com.example.domain.entities.loginResponse.LoginResponse
import com.example.domain.entities.refreshToken.RefreshRequest
import com.example.domain.repositories.RefreshTokenRepository

class RefreshTokenUseCase(private val refreshTokenRepository: RefreshTokenRepository){

    suspend operator fun invoke(refreshRequest: RefreshRequest): Result<LoginResponse> {
        return refreshTokenRepository.refreshTokenRequest(refreshRequest)
    }
}
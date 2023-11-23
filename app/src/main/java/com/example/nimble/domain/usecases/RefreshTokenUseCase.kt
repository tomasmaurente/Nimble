package com.example.nimble.domain.usecases

import com.example.nimble.domain.entities.Result
import com.example.nimble.domain.entities.loginResponse.LoginResponse
import com.example.nimble.domain.entities.refreshToken.RefreshRequest
import com.example.nimble.domain.repositories.RefreshTokenRepository

class RefreshTokenUseCase(private val refreshTokenRepository: RefreshTokenRepository){

    suspend operator fun invoke(refreshRequest: RefreshRequest): Result<LoginResponse> {
        return refreshTokenRepository.refreshTokenRequest(refreshRequest)
    }
}
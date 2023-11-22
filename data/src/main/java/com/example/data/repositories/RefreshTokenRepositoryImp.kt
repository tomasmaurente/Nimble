package com.example.data.repositories

import com.example.data.service.NimbleService
import com.example.domain.entities.Result
import com.example.domain.entities.loginResponse.LoginResponse
import com.example.domain.entities.refreshToken.RefreshRequest
import com.example.domain.repositories.RefreshTokenRepository

class RefreshTokenRepositoryImp(
    private val nimbleService: NimbleService
                                            ): RefreshTokenRepository{

    /**
     * Here goes the logic of choosing if the app is going to use the service info or the room info
     *
     */
    override suspend fun refreshTokenRequest(refreshRequest: RefreshRequest): Result<LoginResponse> {
        return nimbleService.refreshToken(refreshRequest)
    }
}
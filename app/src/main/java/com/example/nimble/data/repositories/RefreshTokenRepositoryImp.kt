package com.example.nimble.data.repositories

import com.example.nimble.data.service.NimbleService
import com.example.nimble.domain.entities.loginResponse.LoginResponse
import com.example.nimble.domain.entities.refreshToken.RefreshRequest
import com.example.nimble.domain.repositories.RefreshTokenRepository
import com.example.nimble.domain.entities.Result


class RefreshTokenRepositoryImp(
    private val nimbleService: NimbleService
                                            ): RefreshTokenRepository {

    /**
     * Here goes the logic of choosing if the app is going to use the service info or the room info
     *
     */
    override suspend fun refreshTokenRequest(refreshRequest: RefreshRequest): Result<LoginResponse> {
        return nimbleService.refreshToken(refreshRequest)
    }
}
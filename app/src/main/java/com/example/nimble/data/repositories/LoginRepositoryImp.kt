package com.example.nimble.data.repositories

import com.example.nimble.data.service.NimbleService
import com.example.nimble.domain.entities.loginResponse.LoginRequest
import com.example.nimble.domain.entities.loginResponse.LoginResponse
import com.example.nimble.domain.repositories.LoginRepository

class LoginRepositoryImp(
    private val nimbleService: NimbleService
): LoginRepository {

    /**
     * Here goes the logic of choosing if the app is going to use the service info or the room info
     *
     */
    override suspend fun loginRequest(loginParameters: LoginRequest): LoginResponse {
        return nimbleService.login(loginParameters)
    }

}
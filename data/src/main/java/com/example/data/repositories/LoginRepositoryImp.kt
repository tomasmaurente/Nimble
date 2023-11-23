package com.example.data.repositories

import com.example.data.service.NimbleService
import com.example.domain.entities.Result
import com.example.domain.entities.loginResponse.LoginRequest
import com.example.domain.entities.loginResponse.LoginResponse
import com.example.domain.repositories.LoginRepository

class LoginRepositoryImp(
    private val nimbleService: NimbleService): LoginRepository{

    /**
     * Here goes the logic of choosing if the app is going to use the service info or the room info
     *
     */
    override suspend fun loginRequest(loginParameters: LoginRequest): LoginResponse {
        return nimbleService.login(loginParameters)
    }
}
package com.example.nimble.domain.usecases

import com.example.nimble.domain.entities.loginResponse.LoginRequest
import com.example.nimble.domain.entities.loginResponse.LoginResponse
import com.example.nimble.domain.repositories.LoginRepository

class LoginUseCase(private val loginRepository: LoginRepository){

    suspend operator fun invoke(loginParameters: LoginRequest): LoginResponse {
        return loginRepository.loginRequest(loginParameters)
    }
}
package com.example.domain.usecases

import com.example.domain.entities.loginResponse.LoginRequest
import com.example.domain.entities.loginResponse.LoginResponse
import com.example.domain.repositories.LoginRepository

class LoginUseCase(private val loginRepository: LoginRepository){

    suspend operator fun invoke(loginParameters: LoginRequest): LoginResponse {
        return loginRepository.loginRequest(loginParameters)
    }
}
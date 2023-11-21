package com.example.domain.usecases

import SurveyDto
import com.example.domain.repositories.GetSurveyPresentationsRepository
import com.example.domain.entities.Result
import com.example.domain.entities.loginResponse.LoginRequest
import com.example.domain.entities.loginResponse.LoginResponse
import com.example.domain.repositories.LoginRepository

class LoginUseCase(private val loginRepository: LoginRepository){

    suspend operator fun invoke(loginParameters: LoginRequest): Result<LoginResponse> {
        return loginRepository.loginRequest(loginParameters)
    }
}
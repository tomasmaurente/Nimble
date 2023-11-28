package com.example.nimble.domain.usecases

import android.util.Patterns
import com.example.nimble.domain.entities.loginResponse.LoginRequest
import com.example.nimble.domain.entities.loginResponse.LoginResponse
import com.example.nimble.domain.repositories.LoginRepository
import com.example.nimble.domain.utils.ErrorHandler

class LoginUseCase(private val loginRepository: LoginRepository){


    // "This code is not necessary, it is here only for testing purposes."

    suspend operator fun invoke(loginParameters: LoginRequest): LoginResponse {
        return if (isValidEmail(loginParameters.email) && isValidPassword(loginParameters.password)) {
            loginRepository.loginRequest(loginParameters)
        } else {
            LoginResponse(error_message = ErrorHandler.PasswordError.errorMessage)
        }
    }

    private fun isValidPassword(password: String): Boolean = password.isNotBlank() && password.length > 5

    private fun isValidEmail(email: String): Boolean  = email.isNotBlank() && email.length > 4 && Patterns.EMAIL_ADDRESS.matcher(email).matches()
}
package com.example.nimble.domain.utils

enum class ErrorHandler(val errorMessage: String) {
    PasswordError("The password or email is wrong, please try again"),
    UnexpectedLoginError("Something went wrong, please try again"),
    ApiKeyError("The api keys are wrong, please contact support"),
}
package com.example.nimble.domain.entities

import com.example.nimble.domain.utils.ErrorHandler

sealed class Result<T> {

    data class Success<T>(val value: T?) : Result<T>()

    data class Failure<T>(val error: ErrorHandler?) : Result<T>()
}
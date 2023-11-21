package com.example.domain.entities

sealed class Result<T> {

    data class Success<T>(val value: T?) : Result<T>()

    data class Failure<T>(val exception: Exception?) : Result<T>()
}
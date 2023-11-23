package com.example.nimble.data.service

import com.example.nimble.domain.entities.loginResponse.LoginRequest
import com.example.nimble.domain.entities.refreshToken.RefreshRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface ApiService {
    @GET
    suspend fun getSurveys(@Url url: String): Response<Map<*, *>>

    @POST
    suspend fun login(@Url url: String, @Body parameters: LoginRequest): Response<Map<*, *>?>

    @POST
    suspend fun refreshToken(@Url url: String, @Body parameters: RefreshRequest): Response<Map<*, *>?>
}
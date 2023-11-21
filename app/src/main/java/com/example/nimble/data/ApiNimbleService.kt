package com.example.nimble.data

import com.example.nimble.dtos.loginResponse.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface ApiNimbleService {
    @GET
    suspend fun getSurveys(@Url url: String): Response<Map<*, *>>

    @POST
    suspend fun login(@Url url: String, @Body parameters: LoginRequest): Response<Map<*, *>?>
}
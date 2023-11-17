package com.example.nimble.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET
    suspend fun getSurveys(@Url url: String): Response<Map<*, *>>
}
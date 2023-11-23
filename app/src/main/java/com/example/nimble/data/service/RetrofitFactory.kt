package com.example.nimble.data.service

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {

    private const val BASE_URL = "https://survey-api.nimblehq.co/api/v1/"

    fun getRetrofitForAuthentication(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
            GsonConverterFactory.create()
        ).build()
    }

    fun getRetrofitForSurveys(accessToken: String): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
            GsonConverterFactory.create()).client(getHttpClient(accessToken)).build()
    }

    private fun getHttpClient(accessToken: String): OkHttpClient = OkHttpClient.Builder().addInterceptor { chain ->
        val original = chain.request()
        val requestBuilder = original.newBuilder().header("Authorization", "Bearer $accessToken")
        val request = requestBuilder.build()
        chain.proceed(request)
    }.build()
}



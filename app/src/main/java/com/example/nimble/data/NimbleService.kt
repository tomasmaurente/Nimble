package com.example.nimble.data

import com.example.nimble.dtos.loginResponse.LoginRequest
import com.example.nimble.dtos.loginResponse.LoginResponse
import com.example.nimble.dtos.surveyListResponse.SurveyAttributesDto
import com.example.nimble.dtos.surveyListResponse.SurveyDto
import com.example.nimble.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NimbleService {

    private val constants = Constants()
    private val retrofit = Retrofit.Builder().baseUrl(constants.apiUrl).addConverterFactory(
        GsonConverterFactory.create()).build()

    suspend fun getSurveys(accessToken: String): List<SurveyDto> {
        val retrofitToken = Retrofit.Builder().baseUrl(constants.apiUrl).addConverterFactory(
            GsonConverterFactory.create()).client(OkHttpClient.Builder().addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder().header("Authorization", "Bearer $accessToken")
            val request = requestBuilder.build()

            chain.proceed(request)
        }.build()).build()

        return withContext(Dispatchers.IO) {
            val response: Response<Map<*, *>> = retrofitToken.create(ApiNimbleService::class.java).getSurveys("surveys?page[number]=1&page[size]=5")
            val surveys: MutableList<SurveyDto> = mutableListOf()

            if(response.isSuccessful) {
                val surveysResponse = response.body()

                if(surveysResponse != null) {
                    if(surveysResponse.containsKey("data")) {
                        val surveysList: List<Map<*, *>> = surveysResponse["data"] as List<Map<*, *>>

                        for(survey in surveysList) {
                            val surveyId: String = survey["id"].toString()
                            val attributes: Map<*, *> = survey["attributes"] as Map<*, *>
                            val title: String = attributes["title"].toString()
                            val description: String = attributes["description"].toString()
                            val coverImageUrl: String = attributes["cover_image_url"].toString()
                            val type: String = attributes["survey_type"].toString()

                            surveys.add(SurveyDto(
                                        SurveyAttributesDto(null, coverImageUrl,null,description,null,null,type,null, null, title),
                                        surveyId,
                                        type)
                            )
                        }
                    }
                }
            }
            surveys
        }
    }

    suspend fun login(parameters: LoginRequest): LoginResponse {
        return withContext(Dispatchers.IO) {
            val response: Response<Map<*, *>?> = retrofit.create(ApiNimbleService::class.java).login("oauth/token", parameters)
            var userInfo = LoginResponse(null, null, null, null, null,"Something went wrong, please try again")

            if(response.isSuccessful) {
                val loginResponse = response.body()

                loginResponse?.let {
                    if(loginResponse.containsKey("data")) {
                        val data: Map<*, *> = loginResponse["data"] as Map<*, *>
                        val attributes: Map<*, *> = data["attributes"] as Map<*, *>
                        val accessToken: String = attributes["access_token"].toString()
                        val tokenType: String = attributes["token_type"].toString()
                        val expiresIn: Long = attributes["expires_in"].toString().toDouble().toLong()
                        val refreshToken: String = attributes["refresh_token"].toString()
                        val createdAt: Long = attributes["created_at"].toString().toDouble().toLong()

                        userInfo = LoginResponse(accessToken, createdAt, expiresIn,  refreshToken, tokenType, null)
                    }

                    if(loginResponse.containsKey("errors")) {
                        val errors: Map<*, *> = loginResponse["errors"] as Map<*, *>
                        val detail: String = errors["detail"].toString()

                        userInfo = LoginResponse(null, null, null, null, null, detail)
                    }
                }
            }
            userInfo
        }
    }
}
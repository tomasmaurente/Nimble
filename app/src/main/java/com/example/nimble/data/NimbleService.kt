package com.example.nimble.data

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

    suspend fun getSurveys(accessToken: String): List<SurveyDto> {
        val retrofitToken = Retrofit.Builder().baseUrl(constants.apiUrl).addConverterFactory(
            GsonConverterFactory.create()).client(OkHttpClient.Builder().addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder().header("Authorization", "Bearer $accessToken")
            val request = requestBuilder.build()

            chain.proceed(request)
        }.build()).build()

        return withContext(Dispatchers.IO) {
            val response: Response<Map<*, *>> = retrofitToken.create(ApiService::class.java).getSurveys("surveys?page[number]=1&page[size]=5")
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
}
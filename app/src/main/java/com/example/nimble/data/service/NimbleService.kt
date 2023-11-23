package com.example.nimble.data.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import com.example.nimble.domain.entities.Result
import com.example.nimble.domain.entities.loginResponse.LoginRequest
import com.example.nimble.domain.entities.loginResponse.LoginResponse
import com.example.nimble.domain.entities.refreshToken.RefreshRequest
import com.example.nimble.domain.entities.surveyListResponse.SurveyAttributesResponse
import com.example.nimble.domain.entities.surveyListResponse.SurveyResponse
import com.example.nimble.domain.utils.ErrorHandler

class NimbleService {

    suspend fun getSurveys(accessToken: String): Result<List<SurveyResponse>> {
        return withContext(Dispatchers.IO) {
            val response: Response<Map<*, *>> = RetrofitFactory.getRetrofitForSurveys(accessToken)
                .create(ApiService::class.java).getSurveys("surveys?page[number]=1&page[size]=5")
            val surveys: MutableList<SurveyResponse> = mutableListOf()

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

                            surveys.add(
                                SurveyResponse(
                                    SurveyAttributesResponse(null, coverImageUrl,null,description,null,null,type,null, null, title),
                                    surveyId,
                                    type)
                            )
                        }
                    }
                }
            }
            Result.Success(surveys)
        }
    }

    suspend fun login(parameters: LoginRequest): LoginResponse {
        var userInfo = LoginResponse(error_message = ErrorHandler.UnexpectedLoginError.errorMessage)
        withContext(Dispatchers.IO) {
            val response: Response<Map<*, *>?> = RetrofitFactory.getRetrofitForAuthentication()
                .create(ApiService::class.java).login("oauth/token", parameters)

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

                        userInfo = LoginResponse(accessToken, createdAt, expiresIn,  refreshToken, tokenType)
                    }
                }
            } else {
                val errorType =
                    if(response.code() == 400){
                        ErrorHandler.PasswordError
                    } else if (response.code() == 401) {
                        ErrorHandler.ApiKeyError
                    } else {
                        ErrorHandler.UnexpectedLoginError
                    }
                userInfo = LoginResponse(error_message = errorType.errorMessage)
            }
        }
        return userInfo
    }

    suspend fun refreshToken(parameters: RefreshRequest): Result<LoginResponse> {
        return withContext(Dispatchers.IO) {
            val response: Response<Map<*, *>?> = RetrofitFactory.getRetrofitForAuthentication()
                .create(ApiService::class.java).refreshToken("oauth/token", parameters)
            var userInfo = LoginResponse(null, null, null, null, null)

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

                        userInfo = LoginResponse(accessToken, createdAt, expiresIn,  refreshToken, tokenType)
                    }

                    if(loginResponse.containsKey("errors")) {
                        val errors: Map<*, *> = loginResponse["errors"] as Map<*, *>
                        val detail: String = errors["detail"].toString()

                        userInfo = LoginResponse(null, null, null, null, null)
                    }
                }
                Result.Success(userInfo)
            } else {
                when(response.errorBody()){
                    else -> Result.Failure(ErrorHandler.UnexpectedLoginError)
                }
            }
        }
    }
}
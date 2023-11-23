package com.example.nimble.data.repositories

import SurveyDto
import com.example.nimble.data.mock.SurveyPresentationMock
import com.example.nimble.data.service.NimbleService
import com.example.nimble.data.utils.SurveyMapper
import com.example.nimble.domain.repositories.GetSurveyPresentationsRepository

import com.example.nimble.domain.entities.Result

class GetSurveyPresentationsRepositoryImp(
    private val surveyService: NimbleService
                                            ): GetSurveyPresentationsRepository {

    /**
     * Here goes the logic of choosing if the app is going to use the service info or the room info
     *
     */

    override suspend fun getSurveyPresentations(accessToken: String): Result<List<SurveyDto>> {
        return getServiceSurveysPresentations(accessToken)
    }
    private suspend fun getServiceSurveysPresentations(accessToken: String): Result<List<SurveyDto>> {
        return when (val result =  surveyService.getSurveys(accessToken)){
            is Result.Success -> {
                Result.Success(result.value?.let { serviceResponse ->
                        SurveyMapper.surveyResponseToSurveyDto(serviceResponse)
                    } ?: SurveyPresentationMock.spMock
                )
            }
            is Result.Failure -> {
                Result.Failure(result.error)
            }
        }
    }
}
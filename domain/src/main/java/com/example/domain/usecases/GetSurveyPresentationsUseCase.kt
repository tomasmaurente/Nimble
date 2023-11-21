package com.example.domain.usecases

import SurveyDto
import com.example.domain.repositories.GetSurveyPresentationsRepository
import com.example.domain.entities.Result

class GetSurveyPresentationsUseCase(private val getSurveyPresentationsRepository: GetSurveyPresentationsRepository){

    suspend operator fun invoke(accessToken: String): Result<List<SurveyDto>> {
        return getSurveyPresentationsRepository.getSurveyPresentations(accessToken)
    }
}
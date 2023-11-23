package com.example.nimble.domain.usecases

import SurveyDto
import com.example.nimble.domain.repositories.GetSurveyPresentationsRepository
import com.example.nimble.domain.entities.Result

class GetSurveyPresentationsUseCase(private val getSurveyPresentationsRepository: GetSurveyPresentationsRepository){

    suspend operator fun invoke(accessToken: String): Result<List<SurveyDto>> {
        return getSurveyPresentationsRepository.getSurveyPresentations(accessToken)
    }
}
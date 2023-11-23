package com.example.nimble.domain.repositories

import SurveyDto
import com.example.nimble.domain.entities.Result

interface GetSurveyPresentationsRepository {
    suspend fun getSurveyPresentations(accessToken: String): Result<List<SurveyDto>>
}
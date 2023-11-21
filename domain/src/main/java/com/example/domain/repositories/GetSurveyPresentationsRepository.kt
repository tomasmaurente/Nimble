package com.example.domain.repositories

import SurveyDto
import com.example.domain.entities.Result

interface GetSurveyPresentationsRepository {
    suspend fun getSurveyPresentations(accessToken: String): Result<List<SurveyDto>>
}
package com.example.nimble.domain.entities.surveyListResponse

data class SurveyListResponse(
    val `data`: List<SurveyResponse>,
    val meta: MetaResponse
)
package com.example.data.responseObjects.surveyListResponse

data class SurveyListResponse(
    val `data`: List<SurveyResponse>,
    val meta: MetaResponse
)
package com.example.nimble.domain.entities.surveyListResponse

data class SurveyAttributesResponse(
    val active_at: String? = null,
    val cover_image_url: String? = null,
    val created_at: String? = null,
    val description: String? = null,
    val inactive_at: Any? = null,
    val is_active: Boolean? = null,
    val survey_type: String? = null,
    val thank_email_above_threshold: String? = null,
    val thank_email_below_threshold: String? = null,
    val title: String? = null
)
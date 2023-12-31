package com.example.nimble.data.utils

import SurveyDto
import com.example.nimble.domain.entities.surveyListResponse.SurveyAttributesDto
import com.example.nimble.domain.entities.surveyListResponse.SurveyAttributesResponse
import com.example.nimble.domain.entities.surveyListResponse.SurveyResponse

object SurveyMapper {

    fun surveyResponseToSurveyDto(surveyResponseList: List<SurveyResponse>): List<SurveyDto>{
        var surveyListDto = mutableListOf<SurveyDto>()
        surveyResponseList.forEach { survey ->
            surveyListDto.add(
                SurveyDto(
                    surveyAttributesResponseToSurveyAttributeDto(
                    survey.attributes),
                    survey.id,
                    survey.type
                )
            )
        }
        return surveyListDto
    }

    private fun surveyAttributesResponseToSurveyAttributeDto(sAtResponse: SurveyAttributesResponse): SurveyAttributesDto {
        return SurveyAttributesDto(
            sAtResponse.active_at,
            sAtResponse.cover_image_url,
            sAtResponse.created_at,
            sAtResponse.description,
            sAtResponse.inactive_at,
            sAtResponse.is_active,
            sAtResponse.survey_type,
            sAtResponse.thank_email_above_threshold,
            sAtResponse.thank_email_below_threshold,
            sAtResponse.title
        )
    }
}
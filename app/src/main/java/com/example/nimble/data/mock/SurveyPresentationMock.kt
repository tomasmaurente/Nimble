package com.example.nimble.data.mock

import SurveyDto
import com.example.nimble.domain.entities.surveyListResponse.SurveyAttributesDto

object SurveyPresentationMock {

    val spMock = listOf(
        SurveyDto(
            SurveyAttributesDto(
            "2015-10-08T07:04:00.000Z",
            "https://dhdbhh0jsld0o.cloudfront.net/m/1ea51560991bcb7d00d0_l",
            "2017-01-23T07:48:12.991Z",
            "We'd love ot hear from you!",
            null,
            true,
            "Restaurant",
            "",
            "",
            "Scarlett Bangkok"),
            "id",
            "type"
        ),
        SurveyDto(
            SurveyAttributesDto(
                "2015-10-08T07:04:00.000Z",
                "https://dhdbhh0jsld0o.cloudfront.net/m/287db81c5e4242412cc0_l",
                "2017-01-23T07:48:12.991Z",
                "Hello world of enthusiasts",
                null,
                true,
                "Restaurant",
                "",
                "",
                "ibis Bangkok Riverside"),
            "id",
            "type"
        )
    )
}
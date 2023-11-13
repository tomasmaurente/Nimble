package com.example.nimble.mock

import com.example.nimble.dtos.SurveyPresentationDto

object surveyPresentationMock {

    val spMock = listOf<SurveyPresentationDto>(
        SurveyPresentationDto("Working from home Check-In","We would like to know how you feel about our work from home..."),
        SurveyPresentationDto("Slide 2","We would like to know how you feel about our work from home..."),
        SurveyPresentationDto("Slide 3 from home Check-In","We would like to know how you feel about our work from home...")
    )

}
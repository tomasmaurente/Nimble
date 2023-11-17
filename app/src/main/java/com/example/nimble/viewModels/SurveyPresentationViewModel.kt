package com.example.nimble.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nimble.dtos.surveyListResponse.SurveyAttributesDto
import com.example.nimble.mock.SurveyPresentationMock
import kotlinx.coroutines.launch

class SurveyPresentationViewModel: ViewModel() {
    private val _surveys = MutableLiveData<List<SurveyAttributesDto>?>()
    val surveyList: LiveData<List<SurveyAttributesDto>?> = _surveys

    fun getSurveys(accessToken: String) {
        viewModelScope.launch {
            val response: List<SurveyAttributesDto> = SurveyPresentationMock.spMock
                //apiClient.getSurveys(accessToken)

            _surveys.value = response
        }
    }
}
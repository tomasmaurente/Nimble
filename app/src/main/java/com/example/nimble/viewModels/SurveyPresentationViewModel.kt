package com.example.nimble.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nimble.data.NimbleService
import com.example.nimble.dtos.surveyListResponse.SurveyDto
import kotlinx.coroutines.launch

class SurveyPresentationViewModel: ViewModel() {
    private val _surveys = MutableLiveData<List<SurveyDto>?>()
    val surveyList: LiveData<List<SurveyDto>?> = _surveys

    private val service = NimbleService()

    fun getSurveys(accessToken: String) {
        viewModelScope.launch {
            val response: List<SurveyDto> = service.getSurveys(accessToken)

            //SurveyPresentationMock.spMock
            _surveys.value = response
        }
    }
}
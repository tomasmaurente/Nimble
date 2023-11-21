package com.example.nimble.viewModel

import SurveyDto
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.mock.SurveyPresentationMock
import com.example.domain.usecases.GetSurveyPresentationsUseCase
import kotlinx.coroutines.launch
import com.example.domain.entities.Result

class SurveyPresentationViewModel(private val getSurveyPresentationsUseCase: GetSurveyPresentationsUseCase): ViewModel() {
    private val _surveys = MutableLiveData<List<SurveyDto>>()
    val surveyList: LiveData<List<SurveyDto>?> = _surveys

    fun getSurveys(accessToken: String) {
        viewModelScope.launch {
            _surveys.value = getSurveyPresentations(accessToken)
        }
    }

    private suspend fun getSurveyPresentations(accessToken: String): List<SurveyDto>{
        return when (val getSurveyPresentations = getSurveyPresentationsUseCase(accessToken)){
            is Result.Success -> getSurveyPresentations.value ?: SurveyPresentationMock.spMock
            else -> SurveyPresentationMock.spMock
        }
    }
}
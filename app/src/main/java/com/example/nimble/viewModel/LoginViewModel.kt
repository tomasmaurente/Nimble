package com.example.nimble.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entities.Result
import com.example.domain.entities.loginResponse.LoginRequest
import com.example.domain.entities.loginResponse.LoginResponse
import com.example.domain.usecases.LoginUseCase
import kotlinx.coroutines.launch

class LoginViewModel(private val loginUseCase: LoginUseCase): ViewModel() {
    private val _loginResponseLiveData = MutableLiveData<LoginResponse>()
    val loginResponseLiveData: LiveData<LoginResponse> = _loginResponseLiveData

    fun login(parameters: LoginRequest) {
        viewModelScope.launch {
            when (val response = loginUseCase(parameters)){
                is Result.Success -> {
                    response.value?.let { nonNullResponse ->
                        _loginResponseLiveData.value = nonNullResponse
                    } ?: run {
                        _loginResponseLiveData.value =
                            LoginResponse(error_message = "An unexpected error occurred, please try again later.")
                    }
                }
                // TODO Improve error management
                is Result.Failure -> {
                    _loginResponseLiveData.value =
                        LoginResponse(error_message = "An unexpected error occurred, please try again later.")
                }
            }
        }
    }
}
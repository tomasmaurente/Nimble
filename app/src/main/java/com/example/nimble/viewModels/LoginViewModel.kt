package com.example.nimble.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nimble.data.NimbleService
import com.example.nimble.dtos.loginResponse.LoginRequest
import com.example.nimble.dtos.loginResponse.LoginResponse
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    private val nimbleService = NimbleService()
    private val _loginResponseLiveData = MutableLiveData<LoginResponse>()
    private val _message = MutableLiveData<String?>()
    val loginResponseLiveData: LiveData<LoginResponse> = _loginResponseLiveData

    fun login(parameters: LoginRequest) {
        viewModelScope.launch {
            val response: LoginResponse = nimbleService.login(parameters)

            _loginResponseLiveData.value = response
        }
    }
}
package com.example.nimble.nimble.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nimble.BuildConfig
import com.example.nimble.domain.entities.loginResponse.LoginRequest
import com.example.nimble.domain.entities.loginResponse.LoginResponse
import com.example.nimble.domain.usecases.LoginUseCase
import kotlinx.coroutines.launch

class LoginViewModel(private val loginUseCase: LoginUseCase): ViewModel() {
    private val _loginResponseLiveData = MutableLiveData<LoginResponse>()
    val loginResponseLiveData: LiveData<LoginResponse> = _loginResponseLiveData


    fun login(email: String, password: String) {
        val parameters = LoginRequest(BuildConfig.API_CLIENT, BuildConfig.API_SECRET, email, "password", password)

        viewModelScope.launch {
            val response = loginUseCase(parameters)
            _loginResponseLiveData.value = response
        }
    }
}
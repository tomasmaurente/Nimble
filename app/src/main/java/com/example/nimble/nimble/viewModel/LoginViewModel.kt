package com.example.nimble.nimble.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nimble.BuildConfig
import com.example.nimble.domain.entities.loginResponse.LoginRequest
import com.example.nimble.domain.entities.loginResponse.LoginResponse
import com.example.nimble.domain.usecases.LoginUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel(private val loginUseCase: LoginUseCase): ViewModel() {

    private val _loginResponseLiveData = MutableLiveData<LoginResponse>()
    val loginResponseLiveData: LiveData<LoginResponse> = _loginResponseLiveData

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _loginEnable = MutableLiveData<Boolean>()
    val loginEnable: LiveData<Boolean> = _loginEnable

    private var loginEnabled: Boolean = false

    private val _showSnack = MutableLiveData<Boolean>()
    var snackContent: String = "Hello world"
    val showSnack: LiveData<Boolean> = _showSnack


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun login(userEmail: String, userPassword: String) {
        val parameters = LoginRequest(BuildConfig.API_CLIENT, BuildConfig.API_SECRET, userEmail, "password", userPassword)

        viewModelScope.launch {
            val response = loginUseCase(parameters)
            _loginResponseLiveData.value = response
        }
    }

    fun onLoginChanged(email: String, password: String) {
        _email.value = email
        _password.value = password
        loginEnabled = isValidEmail(email) && isValidPassword(password)
    }

    fun onLoginSelected() {
        _loginEnable.value = loginEnabled
    }

    fun onForgotPressed() {
        showSnackBar("The forgot password function is not implemented yet")
    }

    fun showSnackBar(snackbarContent: String) {
        viewModelScope.launch {
            snackContent = snackbarContent
            _showSnack.value = true
            delay(4000)
            _showSnack.value = false
        }
    }

    private fun isValidPassword(password: String): Boolean = password.isNotBlank() && password.length > 5

    private fun isValidEmail(email: String): Boolean  = email.isNotBlank() && email.length > 4//Patterns.EMAIL_ADDRESS.matcher(email).matches()
}
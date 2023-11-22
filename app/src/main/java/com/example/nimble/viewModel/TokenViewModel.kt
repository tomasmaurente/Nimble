package com.example.nimble.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entities.Result
import com.example.domain.entities.loginResponse.LoginResponse
import com.example.domain.entities.refreshToken.RefreshRequest
import com.example.domain.usecases.RefreshTokenUseCase
import com.example.nimble.BuildConfig
import kotlinx.coroutines.launch
import java.util.Calendar

class TokenViewModel(private val tokenUseCase: RefreshTokenUseCase): ViewModel() {

    private var currentTime = Calendar.getInstance().timeInMillis

    private lateinit var token: LoginResponse

    fun setToken(tokenData: LoginResponse): Boolean{
        if( tokenData.refresh_token != null &&
            tokenData.created_at != null &&
            tokenData.expires_in != null &&
            tokenData.access_token != null
        ) {
            token = tokenData
            return true
        }
        return false
    }

    fun getToken(): String? {
        return if (::token.isInitialized && token.access_token != null) {
            token.access_token as String
        } else {
            null
        }
    }

    // assuming the variable created_at exists, it is also assumed that the rest will exist.
    fun checkTokenRefresh(){
        if(::token.isInitialized){
            token.created_at?.let {createdAt ->
                val timeToRefresh = createdAt + token.expires_in!!
                if (currentTime > timeToRefresh){
                    refreshToken()
                }
            }
        }

    }
    private fun refreshToken() {
        viewModelScope.launch {
            // knowing the code made it until here, we assume that refresh_token is not null
            val parameters = RefreshRequest(BuildConfig.API_CLIENT, BuildConfig.API_SECRET,"refresh_token", token.refresh_token!!)
            when (val response = tokenUseCase(parameters)){
                is Result.Success -> {
                    response.value?.let { nonNullResponse ->
                        token = nonNullResponse
                    } ?: run {
                        token = LoginResponse(error_message = "An unexpected error occurred, please try again later.")
                    }
                }
                // TODO Improve error management
                is Result.Failure -> {
                    token = LoginResponse(error_message = "An unexpected error occurred, please try again later.")
                }
            }
        }
    }
}
package com.example.nimble.nimble.viewModel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nimble.data.repositories.GetSurveyPresentationsRepositoryImp
import com.example.nimble.data.repositories.LoginRepositoryImp
import com.example.nimble.data.repositories.RefreshTokenRepositoryImp
import com.example.nimble.data.service.NimbleService
import com.example.nimble.domain.usecases.GetSurveyPresentationsUseCase
import com.example.nimble.domain.usecases.LoginUseCase
import com.example.nimble.domain.usecases.RefreshTokenUseCase
import com.example.nimble.nimble.viewModel.LoaderViewModel
import com.example.nimble.nimble.viewModel.LoginViewModel
import com.example.nimble.nimble.viewModel.SurveyPresentationViewModel
import com.example.nimble.nimble.viewModel.TokenViewModel

class ViewModelFactory(): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            SurveyPresentationViewModel::class.java -> {
                SurveyPresentationViewModel(
                    GetSurveyPresentationsUseCase(
                        GetSurveyPresentationsRepositoryImp(
                            NimbleService()
                        )
                    ).apply {  }
                ) as T
            }
            LoginViewModel::class.java -> {
                LoginViewModel(
                    LoginUseCase(
                        LoginRepositoryImp(
                            NimbleService()
                        )
                    ).apply {  }
                ) as T
            }
            LoaderViewModel::class.java -> {
                LoaderViewModel() as T
            }
            TokenViewModel::class.java -> {
                TokenViewModel(
                    RefreshTokenUseCase(
                        RefreshTokenRepositoryImp(
                            NimbleService()
                        )
                    )
                ) as T
            } else -> {
                super.create(modelClass)
            }
        }
    }
}
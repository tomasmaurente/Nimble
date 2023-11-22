package com.example.nimble.viewModel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.example.data.repositories.GetSurveyPresentationsRepositoryImp
import com.example.data.repositories.LoginRepositoryImp
import com.example.data.repositories.RefreshTokenRepositoryImp
import com.example.data.service.NimbleService
import com.example.domain.usecases.GetSurveyPresentationsUseCase
import com.example.domain.usecases.LoginUseCase
import com.example.domain.usecases.RefreshTokenUseCase
import com.example.nimble.viewModel.LoaderViewModel
import com.example.nimble.viewModel.LoginViewModel
import com.example.nimble.viewModel.SurveyPresentationViewModel
import com.example.nimble.viewModel.TokenViewModel

class ViewModelFactory(private val context: Context) : NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
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
            }
            else -> {
                super.create(modelClass)
            }
        }
    }
}
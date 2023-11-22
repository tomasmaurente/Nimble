package com.example.nimble.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nimble.view.LoaderDialog

class LoaderViewModel: ViewModel() {
    private val _loader = MutableLiveData<Boolean?>()
    val loader: LiveData<Boolean?> = _loader

    val loaderDialog = LoaderDialog()

    fun setLoader(data: Boolean?) {
        _loader.value = data
    }
}
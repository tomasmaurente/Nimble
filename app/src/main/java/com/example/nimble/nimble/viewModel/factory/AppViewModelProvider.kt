package com.example.nimble.nimble.viewModel.factory

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

class AppViewModelProvider(activity: FragmentActivity?) : ViewModelProvider(
    (activity as ViewModelStoreOwner),
    ViewModelFactory()
)
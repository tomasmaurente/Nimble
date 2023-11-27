package com.example.nimble.view

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.nimble.R
import com.example.nimble.nimble.viewModel.LoaderViewModel
import com.example.nimble.nimble.viewModel.LoginViewModel
import com.example.nimble.nimble.viewModel.TokenViewModel
import com.example.nimble.nimble.viewModel.factory.AppViewModelProvider
import com.example.nimble.view.composeViews.LoginScreen

class LoginScreenFragment : Fragment() {

    private val loaderViewModel by lazy{
        AppViewModelProvider(activity)[LoaderViewModel::class.java]
    }
    private val tokenViewModel by lazy{
        AppViewModelProvider(activity)[TokenViewModel::class.java]
    }
    private val viewModel by lazy{
        AppViewModelProvider(activity)[LoginViewModel::class.java]
    }

    private lateinit var composeView: ComposeView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return ComposeView(requireContext()).also {
            composeView = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        composeView.setContent {
            LoginScreen(viewModel)
        }

        viewModel.email

        viewModel.loginEnable.observe(viewLifecycleOwner){isEnabled ->
            if (isEnabled) {
                loaderViewModel.setLoader(true)
                viewModel.login(viewModel.email.value.toString(), viewModel.password.value.toString())
            } else {
                viewModel.showSnackBar(getString(R.string.incomplete_fields_text))
            }
        }

        val inputMethodManager: InputMethodManager =
            requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)

        viewModel.loginResponseLiveData.observe(viewLifecycleOwner) { response ->
            if (tokenViewModel.setToken(response)) {
                findNavController().navigate(R.id.action_LoginScreen_to_SurveyPresentationScreen)
            } else {
                loaderViewModel.setLoader(false)
                viewModel.showSnackBar(response.error_message.toString())
            }
        }
    }

}
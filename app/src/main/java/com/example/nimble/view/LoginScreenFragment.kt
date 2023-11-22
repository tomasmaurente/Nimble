package com.example.nimble.view

import android.app.Activity
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.nimble.R
import com.example.nimble.databinding.FragmentLoginScreenBinding
import com.example.nimble.viewModel.LoaderViewModel
import com.example.nimble.viewModel.LoginViewModel
import com.example.nimble.viewModel.TokenViewModel
import com.example.nimble.viewModel.factory.AppViewModelProvider
import com.google.android.material.snackbar.Snackbar

class LoginScreenFragment : Fragment() {

    private var _binding: FragmentLoginScreenBinding? = null
    private val loaderViewModel by lazy{
        AppViewModelProvider(activity).get(LoaderViewModel::class.java)
    }
    private val tokenViewModel by lazy{
        AppViewModelProvider(activity).get(TokenViewModel::class.java)
    }
    private val viewModel by lazy{
        AppViewModelProvider(activity).get(LoginViewModel::class.java)
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginScreenBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            binding.backgroundImage.setRenderEffect(
                RenderEffect.createBlurEffect(20.0f,20.0f,
                    Shader.TileMode.MIRROR))
        }

        binding.buttonLogIn.setOnClickListener {
            val email = binding.emailInput.text.toString().trim()
            val password = binding.passInput.text.toString().trim()

            if(email.isBlank() || password.isBlank()) {
                Snackbar.make(requireActivity().findViewById(android.R.id.content),
                    "You must fill all fields to continue!", Snackbar.LENGTH_LONG).show()
            } else {
                loaderViewModel.setLoader(true)
                viewModel.login(email, password)
            }

            val inputMethodManager: InputMethodManager = requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }

        viewModel.loginResponseLiveData.observe(viewLifecycleOwner){ response ->
                if(tokenViewModel.setToken(response)){
                findNavController().navigate(R.id.action_LoginScreen_to_SurveyPresentationScreen)
            } else {
                loaderViewModel.setLoader(false)
                Snackbar.make(requireActivity().findViewById(android.R.id.content),
                    response.error_message.toString(), Snackbar.LENGTH_LONG).show()
            }
        }

        binding.forgot.setOnClickListener {
            Snackbar.make(requireActivity().findViewById(android.R.id.content),
                "The forgot password function is not implemented yet", Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
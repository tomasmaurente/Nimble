package com.example.nimble.fragments

import android.app.Activity
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.nimble.R
import com.example.nimble.databinding.FragmentLoginScreenBinding
import com.example.nimble.dtos.loginResponse.LoginRequest
import com.example.nimble.utils.Constants
import com.example.nimble.viewModels.LoaderViewModel
import com.example.nimble.viewModels.LoginViewModel
import com.google.android.material.snackbar.Snackbar


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LoginScreenFragment : Fragment() {

    private var _binding: FragmentLoginScreenBinding? = null
    private val loaderViewModel: LoaderViewModel by activityViewModels()
    private val loginViewModel: LoginViewModel by activityViewModels()
    private val constants = Constants()

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
                val parameters = LoginRequest(constants.clientId, constants.clientSecret, email, "password", password)
                loginViewModel.login(parameters)
            }

            val inputMethodManager: InputMethodManager = requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }

        loginViewModel.loginResponseLiveData.observe(viewLifecycleOwner){ response ->
            response.access_token?.let { accessToken ->
                val bundle = Bundle()
                bundle.putString("token", accessToken)
                findNavController().navigate(R.id.action_LoginScreen_to_SurveyPresentationScreen,bundle)

            } ?: run {
                loaderViewModel.setLoader(false)
                Snackbar.make(requireActivity().findViewById(android.R.id.content),
                    response.error_message.toString(), Snackbar.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
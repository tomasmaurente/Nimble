package com.example.nimble.fragments

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.nimble.R
import com.example.nimble.databinding.FragmentLoginScreenBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LoginScreenFragment : Fragment() {

    private var _binding: FragmentLoginScreenBinding? = null

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
            findNavController().navigate(R.id.action_LoginScreen_to_SurveyPresentationScreen)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
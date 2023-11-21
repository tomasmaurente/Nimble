package com.example.nimble.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.nimble.R
import com.example.nimble.databinding.FragmentSurveyBeginnigBinding
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class SurveyBeginningFragment : Fragment() {

    private var _binding: FragmentSurveyBeginnigBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSurveyBeginnigBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backwardsButton.setOnClickListener {
            findNavController().navigate(R.id.action_SurveyBeginningScreen_to_SurveyPresentationScreen)
        }

        var imageSrc : String? = null
        arguments?.let { imageSrc = it.getString("src") as String }
        imageSrc?.let { src ->
            val newImageSrc = src + "l"
            Picasso.get().load(newImageSrc).into(binding.backgroundImage, object : Callback {
                override fun onSuccess() {
                }

                override fun onError(e: Exception?) {
                    binding.backgroundImage.setBackgroundResource(R.drawable.first_survey_background)
                }
            })
        } ?: run {
            Snackbar.make(requireActivity().findViewById(android.R.id.content),
                "Something went wrong, please try again", Snackbar.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_SurveyPresentationScreen_to_LoginScreen)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}


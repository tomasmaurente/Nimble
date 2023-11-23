package com.example.nimble.view

import SurveyDto
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.nimble.R
import com.example.nimble.data.mock.SurveyPresentationMock
import com.example.nimble.databinding.FragmentSurveyPresentationBinding
import com.example.nimble.domain.entities.surveyListResponse.SurveyAttributesDto
import com.example.nimble.nimble.viewModel.factory.AppViewModelProvider
import com.example.nimble.nimble.viewModel.LoaderViewModel
import com.example.nimble.nimble.viewModel.SurveyPresentationViewModel
import com.example.nimble.nimble.adapters.SurveyPresentationAdapter
import com.example.nimble.nimble.viewModel.TokenViewModel
import com.google.android.material.snackbar.Snackbar

class  SurveyPresentationFragment : Fragment() {

    private var _binding: FragmentSurveyPresentationBinding? = null

    private val loaderViewModel by lazy{
        AppViewModelProvider(activity)[LoaderViewModel::class.java]
    }
    private val tokenViewModel by lazy{
        AppViewModelProvider(activity)[TokenViewModel::class.java]
    }
    private val viewModel by lazy{
        AppViewModelProvider(activity)[SurveyPresentationViewModel::class.java]
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewPager: ViewPager2
    private var surveyList: List<SurveyDto>? = null
    private var imgSrc: String? = null

    private val pager = object:ViewPager2.OnPageChangeCallback(){
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            surveyList?.let {
                imgSrc = surveyList!![position].attributes.cover_image_url
                updateView(surveyList!![position].attributes, position)
            } ?: run {
                updateView(SurveyPresentationMock.spMock[position].attributes, position)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSurveyPresentationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager = binding.viewPager

        tokenViewModel.getToken()?.let { token ->
            viewModel.getSurveys(token)
        } ?: run {
            Snackbar.make(requireActivity().findViewById(android.R.id.content),
                "Something went wrong, please try again", Snackbar.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_SurveyPresentationScreen_to_LoginScreen)
        }

        viewModel.surveyList.observe(viewLifecycleOwner) { surveys ->
            surveys?.let {
                surveyList = surveys
            } ?: run {
                surveyList = SurveyPresentationMock.spMock
            }
            setViewPager(surveyList!!)
            loaderViewModel.setLoader(false)

        }
    }

    private fun setViewPager(surveyList: List<SurveyDto>){
        val adapter = SurveyPresentationAdapter(
            surveyList
        ) { onScreenClickListener() }
        viewPager.adapter = adapter
        viewPager.registerOnPageChangeCallback(pager)
        binding.dots.setViewPager2(viewPager)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewPager.unregisterOnPageChangeCallback(pager)
        _binding = null
    }

    private fun onScreenClickListener(){
        val bundle = Bundle()
        bundle.putString("src", imgSrc)
        findNavController().navigate(R.id.action_SurveyPresentationScreen_to_SurveyBeginningScreen, bundle)
    }

    private fun updateView(surveyData: SurveyAttributesDto, position: Int){
        surveyData.title?.let { title ->
            binding.titleHolder.text = title
        }

        surveyData.description?.let { subtitle ->
            binding.surveySubtitle.text = subtitle
        }

        binding.forwardButton.setOnClickListener{
            val newPosition = (position + 1 ) % (surveyList?.size ?: SurveyPresentationMock.spMock.size)
            viewPager.currentItem = newPosition
        }
    }
}

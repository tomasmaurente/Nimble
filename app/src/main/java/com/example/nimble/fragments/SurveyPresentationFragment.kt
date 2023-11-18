package com.example.nimble.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.nimble.R
import com.example.nimble.databinding.FragmentSurveyPresentationBinding
import com.example.nimble.dtos.surveyListResponse.SurveyAttributesDto
import com.example.nimble.dtos.surveyListResponse.SurveyDto
import com.example.nimble.mock.SurveyPresentationMock
import com.example.nimble.viewModels.LoaderViewModel
import com.example.nimble.viewModels.SurveyPresentationViewModel
import com.example.nimble.view_holders.SurveyPresentationAdapter
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class  SurveyPresentationFragment : Fragment() {

    private var _binding: FragmentSurveyPresentationBinding? = null
    private val loaderViewModel: LoaderViewModel by activityViewModels()
    private val surveysViewModel: SurveyPresentationViewModel by activityViewModels()
    private var token: String? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewPager: ViewPager2
    private var surveyList: List<SurveyDto>? = null

    private val pager = object:ViewPager2.OnPageChangeCallback(){
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            surveyList?.let {
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

        arguments?.let { token = it.getString("token") as String }
        token?.let { key ->
            surveysViewModel.getSurveys(key)
        } ?: run {
            Snackbar.make(requireActivity().findViewById(android.R.id.content),
                "Something went wrong, please try again", Snackbar.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_SurveyPresentationScreen_to_LoginScreen)
        }

        surveysViewModel.surveyList.observe(viewLifecycleOwner) { surveys ->
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
        findNavController().navigate(R.id.action_SurveyPresentationScreen_to_SurveyBeginningScreen)
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

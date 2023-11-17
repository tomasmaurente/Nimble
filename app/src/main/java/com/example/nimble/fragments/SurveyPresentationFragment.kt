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
import com.example.nimble.mock.SurveyPresentationMock
import com.example.nimble.viewModels.LoaderViewModel
import com.example.nimble.viewModels.SurveyPresentationViewModel
import com.example.nimble.view_holders.SurveyPresentationAdapter

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class  SurveyPresentationFragment : Fragment() {

    private var _binding: FragmentSurveyPresentationBinding? = null
    private val loaderViewModel: LoaderViewModel by activityViewModels()
    private val surveysViewModel: SurveyPresentationViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewPager: ViewPager2

    private val pager = object:ViewPager2.OnPageChangeCallback(){
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            updateView(SurveyPresentationMock.spMock[position], position)
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
        loaderViewModel.setLoader(true)
        surveysViewModel.getSurveys("hola")

        surveysViewModel.surveyList.observe(viewLifecycleOwner) { surveys ->
            viewPager = binding.viewPager
            setViewPager()
            loaderViewModel.setLoader(false)
        }
    }

    private fun setViewPager(){
        val adapter = SurveyPresentationAdapter(
            SurveyPresentationMock.spMock
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
            val newPosition = (position + 1 ) % (SurveyPresentationMock.spMock.size)     //  Cambiar a tamano de lista!!!!
            viewPager.currentItem = newPosition
        }
    }
}

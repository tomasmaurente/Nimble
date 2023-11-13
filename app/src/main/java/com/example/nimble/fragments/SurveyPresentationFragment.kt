package com.example.nimble.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.nimble.R
import com.example.nimble.databinding.FragmentSurveyPresentationBinding
import com.example.nimble.databinding.FragmentSurveyPresentationCointainerBinding
import com.example.nimble.databinding.WhiteDotBinding
import com.example.nimble.dtos.SurveyPresentationDto
import com.example.nimble.mock.surveyPresentationMock
import com.example.nimble.view_holders.SurveyPresentationAdapter

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SurveyPresentationFragment : Fragment() {

    private var _binding: FragmentSurveyPresentationCointainerBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewPager: ViewPager2

    private val pager = object:ViewPager2.OnPageChangeCallback(){
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)

            updateView(surveyPresentationMock.spMock[position], position)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSurveyPresentationCointainerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager = binding.viewPager
        setViewPager()
    }

    private fun setViewPager(){
        val adapter = SurveyPresentationAdapter(surveyPresentationMock.spMock
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

    private fun updateView(surveyData: SurveyPresentationDto, position: Int){
        surveyData.title?.let { title ->
            binding.titleHolder.text = title
        }

        surveyData.subtitle?.let { subtitle ->
            binding.surveySubtitle.text = subtitle
        }

        binding.forwardButton.setOnClickListener{
            val newPosition = (position + 1 ) % (surveyPresentationMock.spMock.size)     //  Cambiar a tamano de lista!!!!
            viewPager.currentItem = newPosition
        }
    }
}

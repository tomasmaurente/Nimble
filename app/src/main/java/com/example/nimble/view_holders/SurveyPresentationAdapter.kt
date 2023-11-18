package com.example.nimble.view_holders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nimble.databinding.FragmentSurveyPresentationCointainerBinding
import com.example.nimble.dtos.SurveyPresentationDto
import com.example.nimble.dtos.surveyListResponse.SurveyAttributesDto
import com.example.nimble.dtos.surveyListResponse.SurveyDto

class SurveyPresentationAdapter(private val surveyPresentationList: List<SurveyDto>,
                                private val onScreenClickMethod: () -> Unit
                                ): RecyclerView.Adapter<SurveyPresentationViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SurveyPresentationViewHolder {
        return SurveyPresentationViewHolder(
            FragmentSurveyPresentationCointainerBinding.inflate(
            LayoutInflater.from(parent.context),parent,false),
            onScreenClickMethod
        )
    }

    override fun onBindViewHolder(holder: SurveyPresentationViewHolder, position: Int) {
        holder.setView(surveyPresentationList[position].attributes.cover_image_url)
    }

    override fun getItemCount(): Int {
        return surveyPresentationList.size
    }
}
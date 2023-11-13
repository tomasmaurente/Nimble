package com.example.nimble.view_holders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.nimble.databinding.FragmentSurveyPresentationBinding
import com.example.nimble.dtos.SurveyPresentationDto
import kotlinx.coroutines.NonDisposableHandle.parent

class SurveyPresentationAdapter(private val surveyPresentationList: List<SurveyPresentationDto>,
                                private val onScreenClickMethod: () -> Unit
                                ): RecyclerView.Adapter<SurveyPresentationViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SurveyPresentationViewHolder {
        return SurveyPresentationViewHolder(FragmentSurveyPresentationBinding.inflate(
            LayoutInflater.from(parent.context),parent,false),
            onScreenClickMethod
        )
    }

    override fun onBindViewHolder(holder: SurveyPresentationViewHolder, position: Int) {
        holder.setView()
    }

    override fun getItemCount(): Int {
        return surveyPresentationList.size
    }
}
package com.example.nimble.view_holders

import androidx.recyclerview.widget.RecyclerView
import com.example.nimble.databinding.FragmentSurveyPresentationBinding

class SurveyPresentationViewHolder(
    private val binding: FragmentSurveyPresentationBinding,
    private val onScreenClickMethod: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun setView(){
        binding.backgroundImage.setOnClickListener{
            onScreenClickMethod()
        }
    }
}
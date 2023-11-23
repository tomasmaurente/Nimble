package com.example.nimble.nimble.adapters

import androidx.recyclerview.widget.RecyclerView
import com.example.nimble.R
import com.example.nimble.databinding.FragmentSurveyPresentationCointainerBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class SurveyPresentationViewHolder(
    private val binding: FragmentSurveyPresentationCointainerBinding,
    private val onScreenClickMethod: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun setView(imageSrc: String?){
        val newImageSrc = imageSrc + "l"
        Picasso.get().load(newImageSrc).into(binding.backgroundImage, object : Callback {
            override fun onSuccess() {
            }

            override fun onError(e: Exception?) {
                binding.backgroundImage.setBackgroundResource(R.drawable.first_survey_background)
            }
        })

        binding.backgroundImage.setOnClickListener{
            onScreenClickMethod()
        }
    }
}
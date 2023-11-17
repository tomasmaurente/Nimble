package com.example.nimble.view_holders

import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.nimble.R
import com.example.nimble.databinding.FragmentSurveyPresentationCointainerBinding
import com.example.nimble.viewModels.LoaderViewModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class SurveyPresentationViewHolder(
    private val binding: FragmentSurveyPresentationCointainerBinding,
    private val onScreenClickMethod: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun setView(imageSrc: String?){
        Picasso.get().load(imageSrc).into(binding.backgroundImage, object : Callback {
            override fun onSuccess() {
                //loaderViewModel.setLoader(false)
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
package com.example.nimble.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.nimble.databinding.FragmentLoaderBinding
import com.example.nimble.viewModels.LoaderViewModel


class LoaderFragment : DialogFragment() {
    private var _binding: FragmentLoaderBinding? = null
    private val binding get() = _binding!!
    private val loaderViewModel: LoaderViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLoaderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            dialog.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loaderViewModel.loader.observe(viewLifecycleOwner) { loader ->
            if(loader != null) {
                if(!loader) {
                    loaderViewModel.setLoader(null)
                    dismiss()
                }
            }
        }
    }
}
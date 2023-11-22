package com.example.nimble

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import com.example.domain.entities.loginResponse.LoginResponse
import com.example.nimble.databinding.ActivityMainBinding
import com.example.nimble.viewModel.LoaderViewModel
import com.example.nimble.viewModel.TokenViewModel
import com.example.nimble.viewModel.factory.AppViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val loaderViewModel by lazy{
        AppViewModelProvider(this).get(LoaderViewModel::class.java)
    }
    private val tokenViewModel by lazy{
        AppViewModelProvider(this).get(TokenViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsetsCompat.Type.systemBars())
            window.insetsController?.hide(WindowInsetsCompat.Type.statusBars())
        }

        loaderViewModel.loader.observe(this) { loader ->
            if(loader != null && loader && !loaderViewModel.loaderDialog.isVisible) {
                loaderViewModel.loaderDialog.isCancelable = false
                loaderViewModel.loaderDialog.show(supportFragmentManager, "loader_fragment")
            }
        }

        tokenViewModel.checkTokenRefresh()
    }
}
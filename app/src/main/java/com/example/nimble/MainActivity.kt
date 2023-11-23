package com.example.nimble

import android.os.Build
import android.os.Bundle
import com.example.nimble.nimble.viewModel.LoaderViewModel
import com.example.nimble.nimble.viewModel.TokenViewModel
import com.example.nimble.nimble.viewModel.factory.AppViewModelProvider
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import com.example.nimble.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val loaderViewModel by lazy{
        AppViewModelProvider(this)[LoaderViewModel::class.java]
    }
    private val tokenViewModel by lazy{
        AppViewModelProvider(this)[TokenViewModel::class.java]
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
/*
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NimbleTheme {
        Greeting("Android")
    }
}*/
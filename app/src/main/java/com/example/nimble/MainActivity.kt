package com.example.nimble

import android.os.Build
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.example.nimble.databinding.ActivityMainBinding
import com.example.nimble.fragments.LoaderFragment
import com.example.nimble.viewModels.LoaderViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val loaderViewModel: LoaderViewModel by viewModels()
    private val loaderFragment = LoaderFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsetsCompat.Type.systemBars())
            window.insetsController?.hide(WindowInsetsCompat.Type.statusBars())
        }

        loaderViewModel.loader.observe(this) { loader ->
            if(loader != null && loader && !loaderFragment.isVisible) {
                loaderFragment.isCancelable = false
                loaderFragment.show(supportFragmentManager, "loader_fragment")
            }
        }

    }
}
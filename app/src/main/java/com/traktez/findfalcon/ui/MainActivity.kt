package com.traktez.findfalcon.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.traktez.findfalcon.R
import com.traktez.findfalcon.databinding.ActivityMainBinding
import com.traktez.findfalcon.utils.AppPreference
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityMainBinding
    private val binding get() = _binding

    @Inject
    lateinit var appPreference: AppPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_container) as NavHostFragment
        if (appPreference.username.isNotEmpty()) {
            navHostFragment.navController.clearBackStack(R.id.signup)
            navHostFragment.navController.navigate(R.id.bookList)
        }
    }
}
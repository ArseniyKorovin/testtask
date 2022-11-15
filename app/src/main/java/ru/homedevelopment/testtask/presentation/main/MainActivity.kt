package ru.homedevelopment.testtask.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import ru.homedevelopment.testtask.R
import ru.homedevelopment.testtask.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavigation()
    }

    /**
     * Инициализировать навигацию
     */
    private fun initNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fcv_nav_host)
        navHostFragment as NavHostFragment

        val appBarConfiguration = AppBarConfiguration(navHostFragment.navController.graph)

        setSupportActionBar(binding.toolBar)
        binding.toolBar.setupWithNavController(navHostFragment.navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean = findNavController(R.id.fcv_nav_host).navigateUp()
}
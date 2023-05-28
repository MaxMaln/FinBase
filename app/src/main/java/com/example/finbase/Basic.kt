package com.example.finbase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.finbase.databinding.ActivityBasicBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class Basic : AppCompatActivity() {

    private lateinit var binding: ActivityBasicBinding

    private lateinit var sharedpref: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {

        sharedpref = SharedPref(this)

        if(sharedpref.loadNightModeState())
        {
            setTheme(R.style.DarkTheme)
        }
        else
        {
            setTheme(R.style.DayTheme)
        }

        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityBasicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_basic)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_teory,
                R.id.navigation_practic,
                R.id.navigation_calculator,
                R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

}
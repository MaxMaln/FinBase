package com.example.finbase

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.finbase.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var auth: FirebaseAuth

    private lateinit var sharedpref: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {

        sharedpref = SharedPref(this)

        if(sharedpref.loadNightModeState())
        {
            setTheme(R.style.MainDarkTheme)
        }
        else
        {
            setTheme(R.style.MainDayTheme)
        }

        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.authorizationbutton.setOnClickListener {
            val intent = Intent(this@MainActivity, Authorization::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slidein, R.anim.slideout)
        }
        binding.registrationbutton.setOnClickListener {
            val intent = Intent(this@MainActivity, Registration::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slidein, R.anim.slideout)
        }
    }
    public override fun onStart() {
        super.onStart()
        auth = Firebase.auth
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            val intent = Intent(this@MainActivity, Basic::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }
    }
}
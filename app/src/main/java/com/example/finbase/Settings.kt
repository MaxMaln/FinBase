package com.example.finbase
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.example.finbase.databinding.ActivitySettingsBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Settings : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    private lateinit var sharedpref: SharedPref

    @SuppressLint("ClickableViewAccessibility")
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
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(sharedpref.loadNightModeState()){
            binding.switchTheme.isChecked = true
        }

        binding.switchTheme.setOnClickListener {
            if(binding.switchTheme.isChecked)
            {
                sharedpref.setNightModeState(true)
                restartApp()
            }
            else
            {
                sharedpref.setNightModeState(false)
                restartApp()
            }
        }
        binding.switchTheme.setOnTouchListener { _, event -> event.actionMasked == MotionEvent.ACTION_MOVE }

        binding.exitbutton.setOnClickListener {
            Firebase.auth.signOut()
            val intent = Intent(this@Settings, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }
    }
    private fun restartApp(){
        val i = Intent (applicationContext, Settings::class.java)
        startActivity(i)
        overridePendingTransition(0, 0)
        finish()
    }
    @Deprecated("Deprecated in Java",
        ReplaceWith("overridePendingTransition(R.anim.slidein, R.anim.slideout)")
    )
    override fun onBackPressed() {
        val intent = Intent(this@Settings, Basic::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0)
    }
}
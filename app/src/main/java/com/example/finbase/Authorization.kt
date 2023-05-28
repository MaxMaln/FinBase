package com.example.finbase

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finbase.databinding.ActivityAuthorizationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Authorization : AppCompatActivity() {

    private lateinit var binding: ActivityAuthorizationBinding

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
        binding = ActivityAuthorizationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.authorizationbuttonContinue.setOnClickListener {
            signIn(binding.edittextLogin.text.toString().trim(), binding.edittextPassword.text.toString().trim())
        }
    }

    private fun signIn(email: String, password: String) {
        if(binding.edittextPassword.text!!.isEmpty() || binding.edittextLogin.text!!.isEmpty())
        {
            Toast.makeText(this,"Заполните все поля", Toast.LENGTH_LONG).show()
        }
        else
        {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Успешная авторизация, обновить пользовательский интерфейс информацией о авторизированном в системе пользователе
                        Toast.makeText(baseContext, "Авторизация прошла успешно", Toast.LENGTH_SHORT).show()
                        val user = auth.currentUser
                        updateUI(user)
                        val intent = Intent(this@Authorization, Basic::class.java)
                        startActivity(intent)
                        overridePendingTransition(0, 0)
                    }
                    else {
                        // Если выполнить вход не удается, отобразить сообщение пользователю.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Неверный email или пароль", Toast.LENGTH_SHORT).show()
                        updateUI(null)
                    }
                }
        }
    }

    private fun updateUI(user: FirebaseUser?) {

    }

    companion object {
        private const val TAG = "EmailPassword"
    }
    @Deprecated("Deprecated in Java",
        ReplaceWith("overridePendingTransition(R.anim.slidein, R.anim.slideout)")
    )
    override fun onBackPressed() {
        val intent = Intent(this@Authorization, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slidein, R.anim.slideout)
    }
}
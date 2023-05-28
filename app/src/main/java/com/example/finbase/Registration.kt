package com.example.finbase

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finbase.databinding.ActivityRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class Registration : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding

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
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.registrationbuttonContinue.setOnClickListener {
            createAccount(binding.edittextLogin.text.toString().trim(), binding.edittextPassword.text.toString().trim())
        }
    }
    @SuppressLint("SimpleDateFormat")
    private fun createAccount(email: String, password: String) {
        if(binding.edittextPassword.text!!.isEmpty() || binding.edittextPassword2.text!!.isEmpty() || binding.edittextLogin.text!!.isEmpty())
        {
            Toast.makeText(this,"Заполните все поля", Toast.LENGTH_LONG).show()
        }
        else if(binding.edittextPassword.text!!.toString() == binding.edittextPassword2.text!!.toString())
        {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Успешная регистрация, обновить пользовательский интерфейс информацией о зарегистрированном в системе пользователе
                        Log.d(TAG, "createUserWithEmail:success")
                        Toast.makeText(baseContext, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show()
                        val user = auth.currentUser
                        updateUI()

                        val dateregistration = SimpleDateFormat("dd.MM.yyyy")

                        val db = Firebase.firestore
                        val ucer = hashMapOf(
                            "uid" to user?.uid.toString(),
                            "email" to binding.edittextLogin.text.toString(),
                            "dateregistration" to dateregistration.format(Date()),
                            "name" to "",
                            "surname" to "",
                            "patronymic" to "",
                            "datebirthday" to "",
                        )
                        val ucerprogress = hashMapOf(
                            "uid" to user?.uid.toString(),
                            "glava1and2_test" to false,
                            "glava3and4_test" to false,
                            "glava5and6_test" to false,
                            "glava7_test" to false,
                            "glava1and2_sam" to false,
                            "glava3and4_sam" to false,
                            "glava5and6_sam" to false,
                            "glava7_sam" to false,
                        )
                        val ucerindependentwork = hashMapOf(
                            "uid" to user?.uid.toString(),
                            "glava1and2_1" to "",
                            "glava1and2_2" to "",
                            "glava1and2_3" to "",
                            "glava1and2_4" to "",
                            "glava3and4_1" to "",
                            "glava3and4_2" to "",
                            "glava3and4_3" to "",
                            "glava5and6_1" to "",
                            "glava5and6_2" to "",
                            "glava7_1" to "",
                            "glava7_2" to "",
                            "glava7_3" to "",
                        )
                        db.collection("users").document(user?.uid.toString()).set(ucer)
                        db.collection("progress").document(user?.uid.toString()).set(ucerprogress)
                        db.collection("independentworks").document(user?.uid.toString()).set(ucerindependentwork)

                        val intent = Intent(this@Registration, MainActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(0, 0)
                    }
                    else
                    {
                        // Если выполнить вход не удается, отобразить сообщение пользователю.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Проверьте корректность email и длину пароля", Toast.LENGTH_SHORT).show()
                        updateUI()
                    }
                }
        }
        else if(binding.edittextPassword.text.toString() != binding.edittextPassword2.text.toString()){
            Toast.makeText(this,"Пароли не совпадают", Toast.LENGTH_LONG).show()
        }
    }

    private fun updateUI() {}

    companion object {
        private const val TAG = "EmailPassword"
    }

    @Deprecated("Deprecated in Java",
        ReplaceWith("overridePendingTransition(R.anim.slidein, R.anim.slideout)")
    )
    override fun onBackPressed() {
        val intent = Intent(this@Registration, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slidein, R.anim.slideout)
    }
}
package com.example.finbase

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.finbase.databinding.ActivityPracticIndependentworkBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PracticIndependentwork : AppCompatActivity() {

    private lateinit var binding: ActivityPracticIndependentworkBinding

    private lateinit var sharedpref: SharedPref

    private var questionNumber: Int = 0

    private var questions: List<String>? = null

    private var answers = ArrayList<String>()

    private val db : FirebaseFirestore = Firebase.firestore
    private val firebaseUser = FirebaseAuth.getInstance().currentUser

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {

        sharedpref = SharedPref(this)

        if (sharedpref.loadNightModeState()) {

            setTheme(R.style.DarkTheme)
        }
        else {

            setTheme(R.style.DayTheme)
        }

        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityPracticIndependentworkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        start()

        questionNumber +=1

        binding.questionNumber.text = "Задание №$questionNumber"
        binding.questionTextview.text = questions!![questionNumber-1]

        binding.continuue.setOnClickListener {

            val data = mapOf(intent.getStringExtra("glav")+"_"+(questionNumber) to binding.editTextText.text.toString())
            db.collection("independentworks").document(firebaseUser?.uid.toString()).update(data)

            if(questionNumber == questions!!.count())
            {
                val date = mapOf(intent.getStringExtra("glav")+"_sam" to true)
                db.collection("progress").document(firebaseUser?.uid.toString()).update(date)

                val intent = Intent(this@PracticIndependentwork, Basic::class.java)
                startActivity(intent)
                overridePendingTransition(0,0)
                Toast.makeText(this, "Ответы сохранены", Toast.LENGTH_SHORT).show()
            }
            else
            {
                binding.editTextText.setText(answers[questionNumber])
                binding.questionTextview.text = questions!![questionNumber]
                binding.questionNumber.text = "Задание № "+(questionNumber+1)
                questionNumber +=1
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val intent = Intent(this@PracticIndependentwork, Basic::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0)
    }

    private fun start() {
        when {
            intent.getStringExtra("glav") == "glava1and2" -> {
                questions = PracticIndependentworkBase().glava1and2
                val ref = db.collection("independentworks").document(firebaseUser!!.uid)
                ref.get().addOnSuccessListener {
                    if(it != null)
                    {
                        answers.add(it.data?.get(intent.getStringExtra("glav")+"_1")?.toString()!!)
                        answers.add(it.data?.get(intent.getStringExtra("glav")+"_2")?.toString()!!)
                        answers.add(it.data?.get(intent.getStringExtra("glav")+"_3")?.toString()!!)
                        answers.add(it.data?.get(intent.getStringExtra("glav")+"_4")?.toString()!!)
                        binding.editTextText.setText(answers[questionNumber-1])
                    }
                }
                    .addOnFailureListener {}
            }
            intent.getStringExtra("glav") == "glava3and4" -> {
                questions = PracticIndependentworkBase().glava3and4
                val ref = db.collection("independentworks").document(firebaseUser!!.uid)
                ref.get().addOnSuccessListener {
                    if(it != null)
                    {
                        answers.add(it.data?.get(intent.getStringExtra("glav")+"_1")?.toString()!!)
                        answers.add(it.data?.get(intent.getStringExtra("glav")+"_2")?.toString()!!)
                        answers.add(it.data?.get(intent.getStringExtra("glav")+"_3")?.toString()!!)
                        binding.editTextText.setText(answers[questionNumber-1])
                    }
                }
                    .addOnFailureListener {}
            }
            intent.getStringExtra("glav") == "glava5and6" -> {
                questions = PracticIndependentworkBase().glava5and6
                val ref = db.collection("independentworks").document(firebaseUser!!.uid)
                ref.get().addOnSuccessListener {
                    if(it != null)
                    {
                        answers.add(it.data?.get(intent.getStringExtra("glav")+"_1")?.toString()!!)
                        answers.add(it.data?.get(intent.getStringExtra("glav")+"_2")?.toString()!!)
                        binding.editTextText.setText(answers[questionNumber-1])
                    }
                }
                    .addOnFailureListener {}
            }
            intent.getStringExtra("glav") == "glava7" -> {
                questions = PracticIndependentworkBase().glava7
                binding.questionTextview.textSize = "16".toFloat()
                binding.questionTextview.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
                val ref = db.collection("independentworks").document(firebaseUser!!.uid)
                ref.get().addOnSuccessListener {
                    if(it != null)
                    {
                        answers.add(it.data?.get(intent.getStringExtra("glav")+"_1")?.toString()!!)
                        answers.add(it.data?.get(intent.getStringExtra("glav")+"_2")?.toString()!!)
                        answers.add(it.data?.get(intent.getStringExtra("glav")+"_3")?.toString()!!)
                        binding.editTextText.setText(answers[questionNumber-1])
                    }
                }
                    .addOnFailureListener {}
            }
        }
    }
}
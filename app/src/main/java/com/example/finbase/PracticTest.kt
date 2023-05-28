package com.example.finbase

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.finbase.databinding.ActivityPracticTestBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PracticTest : AppCompatActivity() {

    private lateinit var binding: ActivityPracticTestBinding

    private lateinit var sharedpref: SharedPref

    private var testlist: ArrayList<TestClass> = ArrayList()

    private var answerlist: ArrayList<TestClass> = ArrayList()

    private var questionNumber: Int = 0

    private var score: Int = 0

    private var questions: List<String>? = null
    private var answers: List<String>? = null
    private var options: List<String>? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {

        val db : FirebaseFirestore = Firebase.firestore
        val firebaseUser = FirebaseAuth.getInstance().currentUser

        start()

        sharedpref = SharedPref(this)

        if (sharedpref.loadNightModeState()) {

            setTheme(R.style.DarkTheme)
        }
        else {

            setTheme(R.style.DayTheme)
        }

        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityPracticTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.questionNumber.text = "Вопрос №"+(questionNumber+1)
        listview()

        binding.practicContinue.setOnClickListener {

            val intent = Intent(this@PracticTest, Basic::class.java)
            startActivity(intent)
            overridePendingTransition(0,0)
        }
        binding.practicRestart.setOnClickListener {
            binding.questionNumber.text = ""
            binding.questionTextview.text = ""
            binding.testcompleteTextview.text = ""
            binding.testcompleteScore.text = ""
            score = 0
            questionNumber = 0
            answerlist = ArrayList()

            binding.testcompleteTextview.visibility = View.GONE
            binding.questionNumber.visibility = View.VISIBLE
            binding.questionTextview.visibility = View.VISIBLE
            binding.listtest.visibility = View.VISIBLE
            binding.completeLinearlayout.visibility = View.GONE
            binding.practicRestart.visibility = View.GONE
            binding.listanswers.visibility = View.GONE

            binding.questionNumber.text = "Вопрос №"+(questionNumber+1)
            listview()
        }
        binding.practicAnswerlist.setOnClickListener {
            binding.listanswers.adapter = TestAdapter(this, answerlist)
            binding.listanswers.visibility = View.VISIBLE
            binding.testcompleteScore.visibility = View.GONE
            binding.testcompleteTextview.visibility = View.GONE
            binding.practicAnswerlist.visibility = View.GONE
        }

        binding.listtest.setOnItemClickListener { parent, _, position, _ ->

            binding.questionNumber.text = "Вопрос №"+(questionNumber+1)

            if(questionNumber == questions!!.count())
            {
                val item = parent.getItemAtPosition(position) as TestClass
                if(item.Way == answers!![questionNumber-1])
                {
                    score++
                    answerlist.add(TestClass(R.drawable.complete_icon, item.Way))
                }
                else
                {
                    answerlist.add(TestClass(R.drawable.failed_icon, item.Way))
                }
                binding.testcompleteTextview.visibility = View.VISIBLE
                binding.questionNumber.visibility = View.GONE
                binding.questionTextview.visibility = View.GONE
                binding.listtest.visibility = View.GONE
                binding.completeLinearlayout.visibility = View.VISIBLE
                if(score == answers!!.count())
                {
                    val data = mapOf(intent.getStringExtra("glav")+"_test" to true)
                    db.collection("progress").document(firebaseUser?.uid.toString()).update(data)
                    binding.testcompleteTextview.text = "Тест пройден"
                    binding.practicContinue.visibility = View.VISIBLE
                }
                else
                {
                    binding.testcompleteScore.visibility = View.VISIBLE
                    binding.practicContinue.visibility = View.VISIBLE
                    binding.practicRestart.visibility = View.VISIBLE
                    binding.practicAnswerlist.visibility = View.VISIBLE
                    binding.testcompleteTextview.text = "Тест не пройден"
                    binding.testcompleteScore.text = "Правильных ответов: " + score+ "/"+ answers!!.count()
                }

            }
            else
            {
                val item = parent.getItemAtPosition(position) as TestClass
                if(item.Way == answers!![questionNumber-1])
                {
                    score++
                    answerlist.add(TestClass(R.drawable.complete_icon, item.Way))
                }
                else
                {
                    answerlist.add(TestClass(R.drawable.failed_icon, item.Way))
                }
                listview()
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val intent = Intent(this@PracticTest, Basic::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0)
    }

    private fun start() {
        when {
            intent.getStringExtra("glav") == "glava1and2" -> {
                questions = PracticTestsBase().glava1and2[0]
                answers = PracticTestsBase().glava1and2[1]
                options = PracticTestsBase().glava1and2[2]
            }
            intent.getStringExtra("glav") == "glava3and4" -> {
                questions = PracticTestsBase().glava3and4[0]
                answers = PracticTestsBase().glava3and4[1]
                options = PracticTestsBase().glava3and4[2]
            }
            intent.getStringExtra("glav") == "glava5and6" -> {
                questions = PracticTestsBase().glava5and6[0]
                answers = PracticTestsBase().glava5and6[1]
                options = PracticTestsBase().glava5and6[2]
            }
            intent.getStringExtra("glav") == "glava7" -> {
                questions = PracticTestsBase().glava7[0]
                answers = PracticTestsBase().glava7[1]
                options = PracticTestsBase().glava7[2]
            }
        }
    }

    private fun listview() {

        testlist = ArrayList()

        binding.questionTextview.text = questions!![questionNumber]

        for (i in (0..3)) {
            testlist.add(TestClass(R.drawable.option_icon, options!![(questionNumber*4) +i]))
        }
        questionNumber +=1
        binding.listtest.adapter = TestAdapter(this, testlist)
    }
}
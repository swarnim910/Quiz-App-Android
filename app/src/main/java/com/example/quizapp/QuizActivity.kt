package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuizActivity : AppCompatActivity() {

    private lateinit var tvQuestion: TextView
    private lateinit var rgOptions: RadioGroup
    private lateinit var rb1: RadioButton
    private lateinit var rb2: RadioButton
    private lateinit var rb3: RadioButton
    private lateinit var rb4: RadioButton
    private lateinit var btnNext: Button

    private var index = 0
    private var score = 0
    private lateinit var questions: List<Question>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        tvQuestion = findViewById(R.id.tvQuestion)
        rgOptions = findViewById(R.id.rgOptions)
        rb1 = findViewById(R.id.rb1)
        rb2 = findViewById(R.id.rb2)
        rb3 = findViewById(R.id.rb3)
        rb4 = findViewById(R.id.rb4)
        btnNext = findViewById(R.id.btnNext)

        loadQuestions()

        btnNext.setOnClickListener {
            if (::questions.isInitialized) {
                checkAnswer()
                index++

                if (index < questions.size) {
                    showQuestion()
                } else {
                    val intent = Intent(this, ResultActivity::class.java)
                    intent.putExtra("score", score)
                    intent.putExtra("total", questions.size)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    // 🔹 Fetch from API
    private fun loadQuestions() {
        ApiClient.api.getQuestions().enqueue(object : Callback<List<Question>> {
            override fun onResponse(
                call: Call<List<Question>>,
                response: Response<List<Question>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    questions = response.body()!!
                    Toast.makeText(
                        this@QuizActivity,
                        "Loaded: ${questions.size} questions",
                        Toast.LENGTH_SHORT
                    ).show()
                    showQuestion()
                } else {
                    Toast.makeText(
                        this@QuizActivity,
                        "Failed to load questions",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Question>>, t: Throwable) {
                Toast.makeText(
                    this@QuizActivity,
                    "Error: ${t.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    // 🔹 Show current question
    private fun showQuestion() {
        val q = questions[index]
        tvQuestion.text = q.question
        rb1.text = q.option1
        rb2.text = q.option2
        rb3.text = q.option3
        rb4.text = q.option4
        rgOptions.clearCheck()
    }

    // 🔹 Check selected answer
    private fun checkAnswer() {
        val selectedId = rgOptions.checkedRadioButtonId
        if (selectedId != -1) {
            val rb = findViewById<RadioButton>(selectedId)
            if (rb.text.toString() == questions[index].answer) {
                score++
            }
        } else {
            Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show()
        }
    }
}

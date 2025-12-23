package com.example.quizapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val tvResult = findViewById<TextView>(R.id.tvResult)

        val score = intent.getIntExtra("score", 0)
        val total = intent.getIntExtra("total", 0)

        tvResult.text = "Your Score: $score / $total"
    }
}

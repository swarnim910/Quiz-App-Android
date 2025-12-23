package com.example.quizapp.network

import com.example.quizapp.Question
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("quiz/get_questions.php")
    fun getQuestions(): Call<List<Question>>
}

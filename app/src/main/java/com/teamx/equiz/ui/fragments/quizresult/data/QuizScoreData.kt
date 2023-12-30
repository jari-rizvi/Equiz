package com.teamx.equiz.ui.fragments.quizresult.data
import androidx.annotation.Keep

@Keep
data class QuizScoreData(
    val __v: Int,
    val _id: String,
    val quizId: String,
    val score: Int,
    val userId: String
)
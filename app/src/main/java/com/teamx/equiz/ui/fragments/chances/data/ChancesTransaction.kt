package com.teamx.equiz.ui.fragments.chances.data

import androidx.annotation.Keep

@Keep
data class ChancesTransaction(
    val __v: Int,
    val _id: String,
    val chances: Int,
    val quizId: QuizId,
    val timestamp: String,
    val userId: String
)
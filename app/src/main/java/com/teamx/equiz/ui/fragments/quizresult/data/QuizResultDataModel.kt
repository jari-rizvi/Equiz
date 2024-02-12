package com.teamx.equiz.ui.fragments.quizresult.data

import androidx.annotation.Keep

@Keep
data class QuizResultDataModel(
    val populatedQuizScoreData: QuizScoreData,
    var chances: Any
)

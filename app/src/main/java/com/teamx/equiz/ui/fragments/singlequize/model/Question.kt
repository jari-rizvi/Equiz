package com.teamx.equiz.ui.fragments.singlequize.model

import androidx.annotation.Keep

@Keep
data class Question(
    val _id: String,
    val isCorrectIndex: Int,
    val options: List<Options>,
    val questionText: String,
    val questionImage: String?
)
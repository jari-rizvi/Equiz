package com.teamx.equiz.data.models.quizTitleData

import androidx.annotation.Keep

@Keep
data class Data(
    val _id: String,
    val country: String,
    val topic: String,
    val type: String
)
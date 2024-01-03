package com.teamx.equiz.data.models.quizTitleData

import androidx.annotation.Keep

@Keep
data class Data(
    val _id: String,
    val country: String,
    val topic: String,
    val type: String,
    val icon: String,
    val questionLength: Int,
    val startTime: String,
    val endTime: String,
    val isActive: Boolean = true,
    val isRush: Boolean = false,
    val played: Boolean = false
)
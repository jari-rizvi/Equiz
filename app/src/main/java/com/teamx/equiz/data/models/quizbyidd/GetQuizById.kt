package com.teamx.equiz.data.models.quizbyidd
import androidx.annotation.Keep

@Keep
data class GetQuizById(
    val encryptedData: String,
    val iv: String
)
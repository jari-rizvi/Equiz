package com.teamx.equiz.data.models.quizbyidd

data class GetQuizById(
    val encryptedData: String,
    val iv: String
)
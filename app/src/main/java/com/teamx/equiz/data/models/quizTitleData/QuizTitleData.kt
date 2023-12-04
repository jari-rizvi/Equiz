package com.teamx.equiz.data.models.quizTitleData

data class QuizTitleData(
    val `data`: List<Data>,
    val page: Int,
    val totalCount: Int,
    val totalPages: Int
)
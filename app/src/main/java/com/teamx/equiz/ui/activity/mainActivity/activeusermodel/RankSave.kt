package com.teamx.equiz.ui.activity.mainActivity.activeusermodel
import androidx.annotation.Keep

@Keep
data class RankSave(
    val __v: Int,
    val _id: String,
    val activeLevel: String,
    val createdAt: String,
    val points: Double,
    val quizzes: Double,
    val score: Double,
    val time: Int,
    val totalScore: Double,
    val updatedAt: String,
    val userId: String
)
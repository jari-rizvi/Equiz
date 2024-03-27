package com.teamx.equiz.ui.activity.mainActivity.activeusermodel
import androidx.annotation.Keep

@Keep
data class RankSave(
    val __v: Int,
    val _id: String,
    val activeLevel: String,
    val points: Double,
    val profile: Double,
    val profileScore: Int,
    val quizzes: Double,
    val score: Double,
    val time: Double,
    val totalScore: Double,
    val updatedAt: String,
    val userId: String
)
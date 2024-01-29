package com.teamx.equiz.data.models.topWinnerData

import androidx.annotation.Keep

@Keep
data class Game(
    val __v: Int,
    val _id: String,
    val accuracy: Double,
    val calculation: Double,
    val judgment: Double,
    val level: Level,
    val memory: Double,
    val observation: Double,
    val rank: Double,
    val speed: Double,
    val totalScore: Int,
    val userId: UserId
)
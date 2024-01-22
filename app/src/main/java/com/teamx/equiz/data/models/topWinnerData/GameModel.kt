package com.teamx.equiz.data.models.topWinnerData

import androidx.annotation.Keep

@Keep
data class GameModel(
    val __v: Int,
    val _id: String,
    val accuracy: Int,
    val calculation: Int,
    val judgment: Int,
    val memory: Int,
    val observation: Int,
    val rank: Int,
    val speed: Int,
    val totalScore: Int,
    val userId: UserId
)
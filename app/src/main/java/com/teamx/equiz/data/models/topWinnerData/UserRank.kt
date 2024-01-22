package com.teamx.equiz.data.models.topWinnerData

import androidx.annotation.Keep

@Keep
data class UserRank(
    val _id: String,
    val image: String,
    val name: String,
    val rank: Int,
    val score: Double,
    val wallet: Double
)
package com.teamx.equiz.data.models.topWinnerData

import androidx.annotation.Keep

@Keep
data class Game(
    val _id: String,
    val image: String,
    val name: String,
    val rank: Int,
    val score: Double,
    val wallet: Any,
    val bool : Boolean
)




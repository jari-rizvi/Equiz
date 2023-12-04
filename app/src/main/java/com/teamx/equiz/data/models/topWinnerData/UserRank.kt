package com.teamx.equiz.data.models.topWinnerData

data class UserRank(
    val _id: String,
    val image: String,
    val name: String,
    val rank: Int,
    val score: Double,
    val wallet: Int
)
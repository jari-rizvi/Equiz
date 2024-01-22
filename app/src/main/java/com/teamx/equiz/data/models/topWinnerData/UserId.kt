package com.teamx.equiz.data.models.topWinnerData

import androidx.annotation.Keep

@Keep
data class UserId(
    val _id: String,
    val image: String,
    val name: String,
    val wallet: Double?
)
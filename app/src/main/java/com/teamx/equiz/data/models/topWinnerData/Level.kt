package com.teamx.equiz.data.models.topWinnerData
import androidx.annotation.Keep

@Keep
data class Level(
    val Level: String,
    val Next_Level: String,
    val Next_Range: Int,
    val Range: Double
)
package com.teamx.equiz.data.models.staticsData
import androidx.annotation.Keep

@Keep
data class TotalAccumulator(
    val losses: Double,
    val total: Double,
    val wins: Double
)
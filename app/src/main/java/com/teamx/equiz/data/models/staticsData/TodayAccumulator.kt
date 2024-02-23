package com.teamx.equiz.data.models.staticsData
import androidx.annotation.Keep

@Keep
data class TodayAccumulator(
    val losses: Double,
    val total: Double,
    val wins: Double
)
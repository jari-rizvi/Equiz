package com.teamx.equiz.data.models.staticsData

import androidx.annotation.Keep

@Keep
data class StaticsData(
    val todayAccumulator: TodayAccumulator,
    val totalAccumulator: TotalAccumulator
)
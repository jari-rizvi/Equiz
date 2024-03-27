package com.teamx.equiz.data.models.exchangeRate
import androidx.annotation.Keep

@Keep
data class Data(
    val date: String,
    val info: Info,
    val query: Query,
    val result: Double,
    val success: Boolean
)
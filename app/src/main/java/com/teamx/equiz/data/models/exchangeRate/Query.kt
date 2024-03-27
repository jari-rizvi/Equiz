package com.teamx.equiz.data.models.exchangeRate
import androidx.annotation.Keep

@Keep
data class Query(
    val amount: Int,
    val from: String,
    val to: String
)
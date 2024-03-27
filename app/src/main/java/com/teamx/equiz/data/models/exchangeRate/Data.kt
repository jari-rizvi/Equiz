package com.teamx.equiz.data.models.exchangeRate

data class Data(
    val date: String,
    val info: Info,
    val query: Query,
    val result: Double,
    val success: Boolean
)
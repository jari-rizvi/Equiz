package com.teamx.equiz.data.models.getorderData

import androidx.annotation.Keep

@Keep
data class OrderItem(
    val __v: Int,
    val _id: String,
    val product: String,
    val quantity: Int
)
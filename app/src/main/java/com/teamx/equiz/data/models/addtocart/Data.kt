package com.teamx.equiz.data.models.addtocart

import androidx.annotation.Keep

@Keep
data class Data(
    val _id: String,
    val product: String,
    val quantity: Int
)
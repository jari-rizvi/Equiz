package com.teamx.equiz.data.models.getcart

import androidx.annotation.Keep

@Keep
data class Data(
    val product: Product,
    var quantity: Int,
    val totalPrice: Double
)
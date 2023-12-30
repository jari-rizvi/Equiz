package com.teamx.equiz.data.models.orderDetailData

import androidx.annotation.Keep

@Keep
data class ProductDetail(
    val _id: String,
    val product: Product,
    val quantity: Int,
    val totalPrice: Double
)
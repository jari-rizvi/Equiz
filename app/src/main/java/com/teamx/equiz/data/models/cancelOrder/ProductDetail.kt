package com.teamx.equiz.data.models.cancelOrder
import androidx.annotation.Keep

@Keep
data class ProductDetail(
    val _id: String,
    val discount: Int,
    val product: Product,
    val quantity: Int,
    val totalPoint: Int
)
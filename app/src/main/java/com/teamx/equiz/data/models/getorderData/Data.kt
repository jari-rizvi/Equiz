package com.teamx.equiz.data.models.getorderData

import androidx.annotation.Keep

@Keep
data class Data(
    val __v: Int,
    val _id: String,
    val cartDetail: String,
    val createdAt: String,
    val orderID: Int,
    val orderItems: List<OrderItem>,
    val orderStatus: String,
    val paymentIntent: String,
    val shippingInfo: ShippingInfo,
    val totalPoints: Double,
    val totalPrice: Any,
    val user: String
)
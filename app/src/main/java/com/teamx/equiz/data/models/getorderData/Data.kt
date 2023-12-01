package com.teamx.equiz.data.models.getorderData

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
    val totalPoints: Int,
    val totalPrice: Any,
    val user: String
)
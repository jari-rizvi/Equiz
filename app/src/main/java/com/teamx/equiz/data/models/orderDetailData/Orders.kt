package com.teamx.equiz.data.models.orderDetailData

data class Orders(
    val __v: Int,
    val _id: String,
    val cartDetail: String,
    val createdAt: String,
    val orderID: Int,
    val orderItems: List<String>,
    val orderStatus: String,
    val paymentIntent: String,
    val shippingInfo: ShippingInfo,
    val totalPoints: Any,
    val totalPrice: Any,
    val user: User
)
package com.teamx.equiz.data.models.orderDetailData

data class Orders(
    val __v: Int,
    val _id: String,
    val cartDetail: String,
    val coupon: Coupon?,
    val createdAt: String,
    val discountedPrice: Double,
    val orderID: Int,
    val orderItems: List<String>,
    val orderStatus: String,
    val paymentIntent: String,
    val shippingInfo: ShippingInfo,
    val totalPoints: Int,
    val user: User
)
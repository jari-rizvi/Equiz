package com.teamx.equiz.data.models.orderDetailData
import androidx.annotation.Keep

@Keep
data class Orders(
    val __v: Int,
    val _id: String,
    val coupon: Coupon,
    val createdAt: String,
    val discountedPrice: Double,
    val orderID: Int,
    val orderItems: List<String>,
    val orderStatus: String,
    val paymentIntent: String,
    val shippingInfo: ShippingInfo,
    val totalPoints: Double,
    val user: User
)
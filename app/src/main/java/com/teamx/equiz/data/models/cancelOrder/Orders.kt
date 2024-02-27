package com.teamx.equiz.data.models.cancelOrder
import androidx.annotation.Keep

@Keep
data class Orders(
    val __v: Int,
    val _id: String,
    val cartDetail: CartDetail,
    val conversionRate: Int,
    val createdAt: String,
    val orderID: Int,
    val orderItems: List<String>,
    val orderStatus: String,
    val paymentIntent: String,
    val shippingInfo: ShippingInfo,
    val totalPoints: Int,
    val user: User
)
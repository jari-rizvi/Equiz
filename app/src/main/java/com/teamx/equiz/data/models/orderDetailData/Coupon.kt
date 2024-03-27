package com.teamx.equiz.data.models.orderDetailData
import androidx.annotation.Keep

@Keep
data class Coupon(
    val amount: Int,
    val code: String,
    val title: String,
    val type: String
)
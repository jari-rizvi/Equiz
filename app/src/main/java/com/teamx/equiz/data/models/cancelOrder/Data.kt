package com.teamx.equiz.data.models.cancelOrder
import androidx.annotation.Keep

@Keep
data class Data(
    val cartDetail: CartDetail,
    val orders: Orders
)
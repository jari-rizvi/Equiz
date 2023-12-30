package com.teamx.equiz.data.models.orderDetailData

import androidx.annotation.Keep

@Keep
data class Data(
    val cartDetail: CartDetail,
    val orders: Orders
)
package com.teamx.equiz.data.models.cancelOrder
import androidx.annotation.Keep

@Keep
data class ShippingInfo(
    val address: String,
    val label: String,
    val phoneNumber: String
)
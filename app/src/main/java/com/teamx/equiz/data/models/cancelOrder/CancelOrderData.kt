package com.teamx.equiz.data.models.cancelOrder
import androidx.annotation.Keep

@Keep
data class CancelOrderData(
    val `data`: Data,
    val message: String
)
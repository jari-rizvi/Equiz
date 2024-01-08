package com.teamx.equiz.utils.notificationModel
import androidx.annotation.Keep

@Keep
data class ShopAddress(
    val _id: String,
    val city: String,
    val coordinates: List<Double>,
    val country: String,
    val formattedAddress: String,
    val lat: Double,
    val lng: Double,
    val state: String,
    val type: String
)
package com.teamx.equiz.data.models.cancelOrder
import androidx.annotation.Keep

@Keep
data class Product(
    val _id: String,
    val description: String,
    val images: List<String>,
    val isCancelable: Boolean,
    val isCancelled: Boolean,
    val point: Int,
    val title: String
)
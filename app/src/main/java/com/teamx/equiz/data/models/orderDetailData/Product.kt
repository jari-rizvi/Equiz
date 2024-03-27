package com.teamx.equiz.data.models.orderDetailData
import androidx.annotation.Keep

@Keep
data class Product(
    val _id: String,
    val description: String,
    val images: List<String>,
    val isCancelable: Boolean,
    val isCancelled: Boolean,
    val point: Double,
    val title: String
)
package com.teamx.equiz.data.models.getProducts

import androidx.annotation.Keep

@Keep
data class Data(
    val __v: Int,
    val _id: String,
    val brand: String,
    val category: Category,
    val createdAt: String,
    val currency: String,
    val description: String,
    val facilities: List<String>,
    val images: List<String>,
    val isActive: Boolean,
    var isFavorite: Boolean = false,
    val point: Double,
    val price: Double,
    val product_type: String,
    val quantity: Int,
    val ratings: List<Any>,
    val sold: Int,
    val title: String,
    val totalrating: String,
    val updatedAt: String,
    val variations: List<Any>
)
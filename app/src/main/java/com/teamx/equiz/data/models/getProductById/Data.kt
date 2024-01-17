package com.teamx.equiz.data.models.getProductById

import androidx.annotation.Keep

@Keep
data class Data(
    val __v: Int,
    val _id: String,
    val brand: String,
    val category: String,
    val color: List<Any>,
    val createdAt: String,
    val description: String,
    val icon: List<String>,
    val images: List<String>,
    val price: Double,
    val point: Double,
    val product_type: String,
    val quantity: Int,
    val ratings: List<Any>,
    val slug: String,
    val isFavorite: Boolean,
    val sold: Int,
    val title: String,
    val totalrating: String,
    val updatedAt: String,
    val variations: List<Any>
)
package com.teamx.equiz.data.models.wishlistdata

import androidx.annotation.Keep

@Keep
data class Product(
    val __v: Int,
    val _id: String,
    val brand: String,
    val category: String,
    val color: List<String>,
    val createdAt: String,
    val description: String,
    val images: List<String>,
    val price: Double,
    val quantity: Int,
    val ratings: List<Rating>,
    val slug: String,
    val sold: Int,
    val tags: String,
    val title: String,
    val totalrating: String,
    val updatedAt: String,
    val icon: String
)
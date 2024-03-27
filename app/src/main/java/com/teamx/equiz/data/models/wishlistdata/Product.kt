package com.teamx.equiz.data.models.wishlistdata

data class Product(
    val __v: Int,
    val _id: String,
    val category: String,
    val createdAt: String,
    val currency: String,
    val description: String,
//    val facilities: List<Facility>,
    val images: List<String>,
    val isActive: Boolean,
    val isCancelable: Boolean,
    val isFavorite: Boolean,
    val point: Double,
    val product_type: String,
    val quantity: Int,
    val ratings: List<Any>,
    val sold: Int,
    val title: String,
    val totalrating: String,
    val updatedAt: String,
    val variations: List<Any>
)
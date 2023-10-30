package com.teamx.equiz.data.models.getProducts

data class Data(
    val __v: Int,
    val _id: String,
    val brand: String,
    val category: String,
    val color: List<Any>,
    val createdAt: String,
    val currency: String,
    val description: String,
    val facilities: List<String>,
    val icon: List<Any>,
    val images: List<String>,
    val isActive: Boolean,
    val point: Double,
    val price: Double,
    val product_type: String,
    val quantity: Int,
    val ratings: List<Any>,
    val slug: String,
    val sold: Int,
    val title: String,
    val totalrating: String,
    val updatedAt: String,
    val variations: List<Any>
)
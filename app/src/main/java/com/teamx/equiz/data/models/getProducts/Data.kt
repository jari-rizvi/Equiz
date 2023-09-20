package com.teamx.equiz.data.models.getProducts

data class Data(
    val __v: Int,
    val _id: String,
    val brand: String,
    val category: String,
    val color: List<String>,
    val createdAt: String,
    val description: String,
    val icon: String,
    val images: List<String>,
    val name: String,
    val price: Double,
    val quantity: Int,
    val ratings: List<Rating>,
    val slug: String,
    val sold: Int,
    val title: String,
    val totalrating: String,
    val updatedAt: String
)
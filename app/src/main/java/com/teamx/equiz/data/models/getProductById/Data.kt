package com.teamx.equiz.data.models.getProductById
import com.google.errorprone.annotations.Keep

@Keep
data class Data(
    val __v: Int,
    val _id: String,
    val category: Category,
    val createdAt: String,
    val currency: String,
    val description: String,
    val facilities: List<Facility>,
    val images: List<String>,
    val isActive: Boolean,
    val isCancelable: Boolean,
    val isFavorite: Boolean,
    val point: Any,
    val product_type: String,
    val quantity: Int,
    val ratings: List<Any>,
    val sold: Any,
    val title: String,
    val totalrating: String,
    val updatedAt: String,
    val variations: List<Any>
)
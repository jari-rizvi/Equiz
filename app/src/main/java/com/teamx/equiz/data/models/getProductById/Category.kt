package com.teamx.equiz.data.models.getProductById
import com.google.errorprone.annotations.Keep

@Keep
data class Category(
    val _id: String,
    val name: String
)
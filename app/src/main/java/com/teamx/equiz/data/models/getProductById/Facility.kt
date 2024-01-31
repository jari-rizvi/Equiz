package com.teamx.equiz.data.models.getProductById
import com.google.errorprone.annotations.Keep

@Keep
data class Facility(
    val _id: String,
    val icon: String,
    val title: String
)
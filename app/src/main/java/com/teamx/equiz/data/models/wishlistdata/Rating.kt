package com.teamx.equiz.data.models.wishlistdata

import androidx.annotation.Keep

@Keep
data class Rating(
    val _id: String,
    val comment: String,
    val star: Double
)
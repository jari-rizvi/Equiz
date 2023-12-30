package com.teamx.equiz.data.models.getProducts

import androidx.annotation.Keep

@Keep
data class GetProductData(
    val `data`: List<Data>,
    val totalPages: Int,
    val totalProducts: Int
)
package com.teamx.equiz.data.models.getProducts
import com.google.errorprone.annotations.Keep


@Keep
data class GetProductData(
    val `data`: List<Data>,
    val page: Int,
    val totalPages: Int,
    val totalProducts: Int
)
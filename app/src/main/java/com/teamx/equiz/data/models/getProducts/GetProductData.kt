package com.teamx.equiz.data.models.getProducts

data class GetProductData(
    val `data`: List<Data>,
    val page: Int,
    val totalPages: Int,
    val totalProducts: Int
)
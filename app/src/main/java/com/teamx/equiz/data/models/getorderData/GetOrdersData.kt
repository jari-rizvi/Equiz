package com.teamx.equiz.data.models.getorderData

data class GetOrdersData(
    val `data`: List<Data>,
    val page: Int,
    val pageSize: Int,
    val totalPages: Int
)
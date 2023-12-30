package com.teamx.equiz.data.models.getorderData

import androidx.annotation.Keep

@Keep
data class GetOrdersData(
    val `data`: List<Data>,
    val page: Int,
    val pageSize: Int,
    val totalPages: Int
)
package com.teamx.equiz.ui.fragments.ecommerce.home.datanews

import androidx.annotation.Keep


@Keep
data class NewsImagesDataModel(
    val `data`: List<Data>,
    val page: Int,
    val totalCount: Int,
    val totalPages: Int
)
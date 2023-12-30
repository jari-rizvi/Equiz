package com.teamx.equiz.data.models.categoriesData

import androidx.annotation.Keep

@Keep
data class GetAllCategoriesData(
    val data: List<Data>,
    val message: String
)
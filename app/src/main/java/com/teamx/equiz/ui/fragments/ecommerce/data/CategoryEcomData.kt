package com.teamx.equiz.ui.fragments.ecommerce.data

import androidx.annotation.Keep

@Keep
data class CategoryEcomData(
    val category: List<Category>,
    val totalCategories: Int,
    val totalPages: Int
)
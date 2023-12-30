package com.teamx.equiz.ui.fragments.ecommerce.data

import androidx.annotation.Keep

@Keep
data class Category(
    val __v: Int,
    val _id: String,
    val description: String,
    val name: String,
    val updatedAt: String,
    var isChecked: Boolean = true,
)
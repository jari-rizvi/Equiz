package com.teamx.equiz.data.models.categoriesData

import androidx.annotation.Keep

@Keep
data class Data(
    val __v: Int,
    val _id: String,
    val description: String,
    val name: String,
    var isChecked: Boolean
)
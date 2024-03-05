package com.teamx.equiz.data.models.getProducts

import com.google.errorprone.annotations.Keep


@Keep
data class Category(
    val _id: String,
    val name: String
)
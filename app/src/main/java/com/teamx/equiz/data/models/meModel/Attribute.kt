package com.teamx.equiz.data.models.meModel
import androidx.annotation.Keep

@Keep
data class Attribute(
    val _id: String,
    val createdAt: String,
    val description: String,
    val type: String,
    val updatedAt: String,
    val value: Int
)
package com.teamx.equiz.data.models.scratchData

data class Data(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val description: String,
    val image: String,
    val isActive: Boolean,
    val title: String,
    val updatedAt: String,
    val users_choice: List<Any>,
    val value: Int
)
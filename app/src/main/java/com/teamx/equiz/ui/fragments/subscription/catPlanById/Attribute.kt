package com.teamx.equiz.ui.fragments.subscription.catPlanById
import androidx.annotation.Keep

@Keep
data class Attribute(
    val _id: String,
    val createdAt: String,
    val description: String,
    val type: String,
    val updatedAt: String
)
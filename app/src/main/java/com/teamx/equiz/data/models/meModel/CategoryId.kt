package com.teamx.equiz.data.models.meModel

import androidx.annotation.Keep
import com.teamx.equiz.ui.fragments.subscription.catPlanById.Attribute

@Keep
data class CategoryId(
    val __v: Int,
    val _id: String,
    val attributes: List<Attribute>,
    val createdAt: String,
    val image: String,
    val isEnabled: Boolean,
    val title: String,
    val updatedAt: String
)
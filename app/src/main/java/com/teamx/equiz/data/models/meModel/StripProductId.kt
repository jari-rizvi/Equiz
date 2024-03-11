package com.teamx.equiz.data.models.meModel

import androidx.annotation.Keep

@Keep
data class StripProductId(
    val __v: Int,
    val _id: String,
    val categoryId: CategoryId?,
    val createdAt: String,
    val freeTrialDay: Int,
    val interval: String,
    val intervalCount: Int,
    val isActive: Boolean,
    val isBase: Boolean,
    val isFree: Boolean,
    val name: String,
    val price: Int,
    val stripeId: String,
    val stripePlaneId: String,
    val updatedAt: String
)
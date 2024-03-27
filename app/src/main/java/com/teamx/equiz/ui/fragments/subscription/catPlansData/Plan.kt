package com.teamx.equiz.ui.fragments.subscription.catPlansData
import androidx.annotation.Keep

@Keep
data class Plan(
    val __v: Int,
    val _id: String,
    val categoryId: String,
    val createdAt: String,
    val freeTrialDay: Int,
    val interval: String,
    val intervalCount: Int,
    val isActive: Boolean,
    val isBase: Boolean,
    val isFree: Boolean,
    val name: String,
    val price: Double,
    val stripeId: String,
    val stripePlaneId: String,
    val updatedAt: String
)
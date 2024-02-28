package com.teamx.equiz.ui.fragments.subscription.plansData

data class Data(
    val __v: Int,
    val _id: String,
    val bullets: List<String>,
    val createdAt: String,
    val freeTrialDay: Int,
    val image: String,
    val interval: String,
    val intervalCount: Int,
    val isActive: Boolean,
    val isFree: Boolean,
    val name: String,
    val price: Int,
    val stripeId: String,
    val stripePlaneId: String,
    val updatedAt: String
)
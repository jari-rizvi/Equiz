package com.teamx.equiz.ui.fragments.subscription.catPlanById

data class CatPlanById(
    val __v: Int,
    val _id: String,
    val attributes: List<Attribute>,
    val createdAt: String,
    val image: String,
    val isEnabled: Boolean,
    val plans: List<Plan>,
    val title: String,
    val updatedAt: String
)
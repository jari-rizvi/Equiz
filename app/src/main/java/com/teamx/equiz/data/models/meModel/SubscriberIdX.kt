package com.teamx.equiz.data.models.meModel
import androidx.annotation.Keep

@Keep
data class SubscriberIdX(
    val __v: Int,
    val _id: String,
    val client_secret: String,
    val createdAt: String,
    val endDate: String,
    val planeId: String,
    val recurred: Int,
    val startDate: String,
    val status: String,
    val stripeItemId: String,
    val stripeSubscriberId: String,
    val updatedAt: String,
    val userId: String
)
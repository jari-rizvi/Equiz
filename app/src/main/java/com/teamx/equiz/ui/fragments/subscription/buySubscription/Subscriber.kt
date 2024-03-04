package com.teamx.equiz.ui.fragments.subscription.buySubscription

data class Subscriber(
    val _id: String,
    val client_secret: String,
    val endDate: String,
    val planeId: String,
    val startDate: String,
    val status: String,
    val stripeSubscriberId: String,
    val userId: String
)
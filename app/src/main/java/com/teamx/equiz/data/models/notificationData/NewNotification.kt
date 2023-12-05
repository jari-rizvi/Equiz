package com.teamx.equiz.data.models.notificationData

data class NewNotification(
    val __v: Int,
    val _id: String,
    val body: String,
    val createdAt: String,
    val read: Boolean,
    val title: String,
    val userId: String
)
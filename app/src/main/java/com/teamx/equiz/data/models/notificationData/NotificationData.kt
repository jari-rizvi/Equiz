package com.teamx.equiz.data.models.notificationData

data class NotificationData(
    val message: String,
    val newNotification: List<NewNotification>,
    val page: Int,
    val pageSize: Int,
    val totalPages: Int
)
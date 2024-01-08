package com.teamx.equiz.utils.notificationModel
import androidx.annotation.Keep

@Keep
data class NotificationModell(
    val actionText: String,
    val img: String,
    val orderID: String,
    val orderStatus: String,
    val shopDetail: ShopDetail,
    val type: String
)
package com.teamx.equiz.utils.notificationModel

import androidx.annotation.Keep

@Keep
data class ShopDetail(
    val contact: String,
    val shopAddress: ShopAddress,
    val shopLogo: String,
    val shopName: String
)
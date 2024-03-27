package com.teamx.equiz.data.models.orderDetailData
import androidx.annotation.Keep

@Keep
data class User(
    val email: String,
    val id: Any,
    val name: String,
    val phone: String,
    val profileProgress: Int
)
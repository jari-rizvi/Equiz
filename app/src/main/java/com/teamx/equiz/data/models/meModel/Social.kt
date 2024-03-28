package com.teamx.equiz.data.models.meModel
import androidx.annotation.Keep

@Keep
data class Social(
    val _id: String,
    val label: String,
    val isConnected: Boolean,
    val token: String,
    val name: String,

)
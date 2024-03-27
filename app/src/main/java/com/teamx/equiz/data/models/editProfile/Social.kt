package com.teamx.equiz.data.models.editProfile
import androidx.annotation.Keep

@Keep
data class Social(
    val _id: String,
    val label: String,
    val link: String
)
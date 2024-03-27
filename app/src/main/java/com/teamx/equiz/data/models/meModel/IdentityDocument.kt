package com.teamx.equiz.data.models.meModel
import androidx.annotation.Keep

@Keep
data class IdentityDocument(
    val _id: String,
    val isVerified: Boolean,
    val link: String,
    val title: String,
    val type: String
)
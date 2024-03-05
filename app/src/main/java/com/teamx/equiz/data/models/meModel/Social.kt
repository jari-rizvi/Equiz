package com.teamx.equiz.data.models.meModel
import com.google.errorprone.annotations.Keep


@Keep
data class Social(
    val _id: String,
    val label: String,
    val link: String
)
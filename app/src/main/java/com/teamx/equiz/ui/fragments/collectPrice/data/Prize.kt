package com.teamx.equiz.ui.fragments.collectPrice.data

import androidx.annotation.Keep

@Keep
data class Prize(
    val _id: String,
    val description: String,
    val image: String,
    val title: String
)
package com.teamx.equiz.ui.fragments.collectPrice.data

import androidx.annotation.Keep

@Keep
data class Raffle(
    val _id: String,
    val name: String,
    val prize: Prize
)
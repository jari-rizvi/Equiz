package com.teamx.equiz.ui.fragments.topup.data

import androidx.annotation.Keep

@Keep
data class TopUpModelData(
    val checkout: String,
    val `data`: Int,
    val message: String
)
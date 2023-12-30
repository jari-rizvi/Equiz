package com.teamx.equiz.ui.fragments.chances.data

import androidx.annotation.Keep

@Keep
data class ChancesModelData(
    val chancesTransaction: List<ChancesTransaction>,
    val page: Int,
    val totalChanceCount: Int,
    val totalPages: Int,
    val userChances: UserChances
)
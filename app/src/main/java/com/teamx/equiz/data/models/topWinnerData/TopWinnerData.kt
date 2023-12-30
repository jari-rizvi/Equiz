package com.teamx.equiz.data.models.topWinnerData

import androidx.annotation.Keep

@Keep
data class TopWinnerData(
    val game: List<Game>,
    val page: Int,
    val totalPages: Int,
    val totalUsersCount: Int,
    val userRank: List<UserRank>
)
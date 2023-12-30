package com.teamx.equiz.data.models.sucessData.gamesuccess

import androidx.annotation.Keep

@Keep
data class Game(
    val fcmToken: String,
    val score: Int
)
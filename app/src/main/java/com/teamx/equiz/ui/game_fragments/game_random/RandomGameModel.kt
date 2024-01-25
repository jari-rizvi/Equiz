package com.teamx.equiz.ui.game_fragments.game_random

import com.teamx.equiz.ui.fragments.dashboard.GamesUID2

data class RandomGameModel(
    val gameName: String,
    val gamesUID2: GamesUID2,
    var gameUsed: Boolean = false
)
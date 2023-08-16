package com.teamx.equiz.data.models

data class MusicResponseModel(
    val resultCount: Int,
    val results: List<MusicModel>?,
)
package com.teamx.equiz.ui.fragments.singlequize.model

import androidx.annotation.Keep

@Keep
data class SingleQuizData(
    var `data`: List<Data>?,
    val page: Int,
    val totalCount: Int,
    val totalPages: Int
)
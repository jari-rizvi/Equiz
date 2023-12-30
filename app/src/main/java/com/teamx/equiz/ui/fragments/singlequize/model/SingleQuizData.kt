package com.teamx.equiz.ui.fragments.singlequize.model

import androidx.annotation.Keep

@Keep
data class SingleQuizData(
    val `data`: List<Data>,
    val page: Int,
    val totalCount: Int,
    val totalPages: Int
)
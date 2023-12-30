package com.teamx.equiz.ui.fragments.singlequize.model

import androidx.annotation.Keep

@Keep
data class Data(
    val __v: Int,
    val _id: String,
    val country: String,
    val createdAt: String,
    val description: String,
    val endDate: String,
    val endTime: String,
    val icon: String,
    val isActive: Boolean,
    val points: Int,
    val question: List<Question>,
    val replay: Boolean,
    val startDate: String,
    val startTime: String,
    val title: String,
    val topic: String,
    val type: String
)
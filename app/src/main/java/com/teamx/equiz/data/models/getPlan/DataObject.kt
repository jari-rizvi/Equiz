package com.teamx.equiz.data.models.getPlan

import androidx.annotation.Keep

@Keep
data class DataObject(
    val amount: Int,
    val currency: String,
    val description: String,
    val id: String,
    val plan_id: String,
    val title: String
)
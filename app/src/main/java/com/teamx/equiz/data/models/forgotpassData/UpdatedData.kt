package com.teamx.equiz.data.models.forgotpassData

data class UpdatedData(
    val acknowledged: Boolean,
    val matchedCount: Int,
    val modifiedCount: Int,
    val upsertedCount: Int,
    val upsertedId: Any
)
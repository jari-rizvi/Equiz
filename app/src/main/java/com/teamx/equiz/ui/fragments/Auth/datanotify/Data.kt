package com.teamx.equiz.ui.fragments.Auth.datanotify

import androidx.annotation.Keep

@Keep
data class Data(
    val __v: Int,
    val _id: String,
    val body: String,
    val createdAt: String,
    val read: Boolean,
    val title: String,
    val userId: String
)
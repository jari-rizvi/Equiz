package com.teamx.equiz.ui.fragments.ecommerce.productProfile.unsub_data
import androidx.annotation.Keep

@Keep
data class Items(
    val `data`: List<Data>,
    val has_more: Boolean,
    val `object`: String,
    val total_count: Int,
    val url: String
)
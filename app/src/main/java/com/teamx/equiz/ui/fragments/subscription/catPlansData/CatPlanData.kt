package com.teamx.equiz.ui.fragments.subscription.catPlansData

data class CatPlanData(
    val page: Int,
    val `data`: List<Data>,
    val pageSize: Int,
    val totalDocs: Int,
    val totalPages: Int
)
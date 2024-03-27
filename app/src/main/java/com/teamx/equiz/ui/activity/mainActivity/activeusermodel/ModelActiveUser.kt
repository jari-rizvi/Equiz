package com.teamx.equiz.ui.activity.mainActivity.activeusermodel
import androidx.annotation.Keep

@Keep
data class ModelActiveUser(
    val activeLevel: ActiveLevel,
    val rankSave: RankSave
)
package com.teamx.equiz.data.models.editProfile
import androidx.annotation.Keep

@Keep
data class IdentityDocument(
    var title: String?,
    var isVerified: Boolean?,
    var link: String,
    var type: String?
)
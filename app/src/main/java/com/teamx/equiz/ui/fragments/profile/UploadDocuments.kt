package com.teamx.equiz.ui.fragments.profile

import com.teamx.equiz.data.models.editProfile.IdentityDocument
import androidx.annotation.Keep

@Keep
data class UploadDocuments(
    val identityDocuments: List<IdentityDocument>
)
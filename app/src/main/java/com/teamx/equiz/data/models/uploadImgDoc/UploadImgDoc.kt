package com.teamx.equiz.data.models.uploadImgDoc
import androidx.annotation.Keep

@Keep
data class UploadImgDoc(
    val `data`: List<String>,
    val message: String
)
package com.teamx.equiz.data.models.meModel
import androidx.annotation.Keep
import com.teamx.equiz.data.models.loginData.User

@Keep
data class MeModel(
    val user: User
)
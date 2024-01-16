package com.teamx.equiz.ui.fragments.address.dataclasses.getAddressList

import androidx.annotation.Keep

@Keep

data class Data(
    val _id: String,
    val address: String,
    val phoneNumber: String,
    val label: String,
    var value: Boolean = false

)
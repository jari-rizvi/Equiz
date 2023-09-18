package com.teamx.equiz.data.models

class PaymentMethod(
    val paymentId: Int,
    val paymentImage: Int,
    val paymentName: String,
    var value: Boolean = false
)

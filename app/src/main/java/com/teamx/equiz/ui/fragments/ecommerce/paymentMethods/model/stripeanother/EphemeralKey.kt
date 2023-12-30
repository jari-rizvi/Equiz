package com.teamx.equiz.ui.fragments.ecommerce.paymentMethods.model.stripeanother
import androidx.annotation.Keep

@Keep
data class EphemeralKey(
    val associated_objects: List<AssociatedObject>,
    val created: Int,
    val expires: Int,
    val id: String,
    val livemode: Boolean,
    val `object`: String,
    val secret: String
)
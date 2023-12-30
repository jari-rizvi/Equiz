package com.teamx.equiz.ui.fragments.ecommerce.checkout

interface OnCartListener {
//
    fun onAddClickListener(position: Int)
    fun onSubClickListener(position: Int)
//    fun onAddToCartListener(id: Int)
    fun onRemoveToCartListener(position: Int)
}
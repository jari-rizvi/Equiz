package com.teamx.equiz.ui.fragments.ecommerce.home

interface OnProductListener {

    fun onproductClick(position : Int)
    fun onAddFavClick(position : Int,isFav : Boolean)

}
package com.teamx.equiz

import androidx.lifecycle.MutableLiveData
import com.teamx.equiz.baseclasses.BaseViewModel
import com.teamx.equiz.data.models.ProductModel
import com.teamx.equiz.ui.fragments.dashboard.GamesUID2


/**
 * Shared View Model class for sharing data between fragments
 */
class SharedViewModel : BaseViewModel() {

    val clickOnContinueBtn: MutableLiveData<Boolean>? = null

    val addToCartProduct = MutableLiveData<ArrayList<ProductModel>>()

    fun addProduct(model: ProductModel) {
        val list = addToCartProduct.value;
        if (list != null) {
            list.add(model);
            addToCartProduct.postValue(list!!)
        } else {
            val newList = ArrayList<ProductModel>();
            newList.add(model)
            addToCartProduct.postValue(newList)
        }
    }

    fun removeProduct(deleteModel: ProductModel) {
        val list = addToCartProduct.value
        if (list != null && list.size > 0) {
            var index = 0;
            var found = false
            while (!found && index < list.size) {
                if (list.get(index).id == deleteModel.id) {
                    list.removeAt(index)
                    found = true
                }
                index++;
            }
            if (list != null && list.size > 0) {
                addToCartProduct.postValue(list!!)
            } else {
                addToCartProduct.postValue(ArrayList())
            }
        }
    }


    var roundInteger = 1
    var stateOfGameFrags = 0
    var gameName = arrayListOf<String>()
    var gameNameRight = arrayListOf<Double>()
    var gameNameTotal = arrayListOf<Double>()
    var gamesTotalScore = 0
    var gamesRightScore = 0


}
package com.teamx.equiz.ui.fragments.address

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.teamx.equiz.baseclasses.BaseViewModel
import com.teamx.equiz.data.models.addtowishlist.AddToWishlistData
import com.teamx.equiz.data.models.wishlistdata.WishlistData
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.data.remote.reporitory.MainRepository
import com.teamx.equiz.ui.fragments.address.dataclasses.AddressOrderCreate
import com.teamx.equiz.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {

    private val _createOrderResponse = MutableLiveData<Resource<AddressOrderCreate>>()
    val createOrderResponse: LiveData<Resource<AddressOrderCreate>>
        get() = _createOrderResponse

    fun createOrder(param: JsonObject) {
        viewModelScope.launch {
            _createOrderResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.createOrder(param).let {
                        if (it.isSuccessful) {
                            _createOrderResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 401) {
//                            unAuthorizedCallback.onToSignUpPage()
                            _createOrderResponse.postValue(Resource.error(it.message(), null))
                        } else if (it.code() == 401) {
                            _createOrderResponse.postValue(Resource.unAuth("", null))
                        } else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
//                            _createOrderResponse.postValue(Resource.error(it.message(), null))
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _createOrderResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _createOrderResponse.postValue(
                                Resource.error(
                                    "Some thing went wrong",
                                    null
                                )
                            )
                        }
                    }
                } catch (e: Exception) {
                    _createOrderResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _createOrderResponse.postValue(Resource.error("No internet connection", null))
        }
    }



}
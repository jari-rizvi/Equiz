package com.teamx.equiz.ui.fragments.ecommerce.checkout


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.teamx.equiz.baseclasses.BaseViewModel
import com.teamx.equiz.data.models.getcart.GetCartData
import com.teamx.equiz.data.models.sucessData.SuccessData
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.data.remote.reporitory.MainRepository
import com.teamx.equiz.ui.fragments.address.dataclasses.AddressOrderCreate
import com.teamx.equiz.ui.fragments.address.dataclasses.getAddressList.GetAddressListData
import com.teamx.equiz.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
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


    private val _getcartResponse = MutableLiveData<Resource<GetCartData>>()
    val getcartResponse: LiveData<Resource<GetCartData>>
        get() = _getcartResponse

    fun getCart() {
        viewModelScope.launch {
            _getcartResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    Timber.tag("87878787887").d("starta")

                    mainRepository.getCart().let {
                        if (it.isSuccessful) {
                            _getcartResponse.postValue(Resource.success(it.body()!!))
                            Timber.tag("87878787887").d(it.body()!!.toString())
                        } else if (it.code() == 401) {
                            _getcartResponse.postValue(Resource.unAuth("", null))
                        } else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
                            Timber.tag("87878787887").d("secoonnddd")

//                            _getcartResponse.postValue(Resource.error(it.message(), null))
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _getcartResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _getcartResponse.postValue(
                                Resource.error(
                                    "Some thing went wrong",
                                    null
                                )
                            )
                            Timber.tag("87878787887").d("third")

                        }
                    }
                } catch (e: Exception) {
                    _getcartResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _getcartResponse.postValue(Resource.error("No internet connection", null))
        }
    }

    private val _applyCouponResponse = MutableLiveData<Resource<GetCartData>>()
    val applyCouponResponse: LiveData<Resource<GetCartData>>
        get() = _applyCouponResponse

    fun applyCoupon(code: String) {
        viewModelScope.launch {
            _applyCouponResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    Timber.tag("87878787887").d("starta")

                    mainRepository.applyCoupon(code).let {
                        if (it.isSuccessful) {
                            _applyCouponResponse.postValue(Resource.success(it.body()!!))
                            Timber.tag("87878787887").d(it.body()!!.toString())
                        } else if (it.code() == 401) {
                            _applyCouponResponse.postValue(Resource.unAuth("", null))
                        } else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
                            Timber.tag("87878787887").d("secoonnddd")

//                            _applyCouponResponse.postValue(Resource.error(it.message(), null))
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _applyCouponResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _applyCouponResponse.postValue(
                                Resource.error(
                                    "Some thing went wrong",
                                    null
                                )
                            )
                            Timber.tag("87878787887").d("third")

                        }
                    }
                } catch (e: Exception) {
                    _applyCouponResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _applyCouponResponse.postValue(Resource.error("No internet connection", null))
        }
    }


    private val _deleteCartResponse = MutableLiveData<Resource<SuccessData>>()
    val deleteCartResponse: LiveData<Resource<SuccessData>>
        get() = _deleteCartResponse


    fun deleteCart(productId: String) {
        viewModelScope.launch {
            _deleteCartResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    Timber.tag("87878787887").d("starta")

                    mainRepository.deleteCart(productId).let {
                        if (it.isSuccessful) {
                            _deleteCartResponse.postValue(Resource.success(it.body()!!))
                            Timber.tag("87878787887").d(it.body()!!.toString())
                        } else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
                            Timber.tag("87878787887").d("secoonnddd")

//                            _deleteCartResponse.postValue(Resource.error(it.message(), null))
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _deleteCartResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _deleteCartResponse.postValue(
                                Resource.error(
                                    "Some thing went wrong",
                                    null
                                )
                            )
                            Timber.tag("87878787887").d("third")

                        }
                    }
                } catch (e: Exception) {
                    _deleteCartResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _deleteCartResponse.postValue(Resource.error("No internet connection", null))
        }
    }


    private val _updateCartResponse = MutableLiveData<Resource<SuccessData>>()
    val updateCartResponse: LiveData<Resource<SuccessData>>
        get() = _updateCartResponse


    fun updateCart(productId: JsonObject) {
        viewModelScope.launch {
            _updateCartResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    Timber.tag("87878787887").d("starta")

                    mainRepository.updateCart(productId).let {
                        if (it.isSuccessful) {
                            _updateCartResponse.postValue(Resource.success(it.body()!!))
                            Timber.tag("87878787887").d(it.body()!!.toString())
                        } else if (it.code() == 401) {
                            _updateCartResponse.postValue(Resource.unAuth("", null))
                        } else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
                            Timber.tag("87878787887").d("secoonnddd")

//                            _updateCartResponse.postValue(Resource.error(it.message(), null))
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _updateCartResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _updateCartResponse.postValue(
                                Resource.error(
                                    "Some thing went wrong",
                                    null
                                )
                            )
                            Timber.tag("87878787887").d("third")

                        }
                    }
                } catch (e: Exception) {
                    _updateCartResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _updateCartResponse.postValue(Resource.error("No internet connection", null))
        }
    }



    private val _addressListResponse = MutableLiveData<Resource<GetAddressListData>>()
    val addressList: LiveData<Resource<GetAddressListData>>
        get() = _addressListResponse

    fun getAddressList() {
        viewModelScope.launch {
            _addressListResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.getAddressList().let {
                        if (it.isSuccessful) {
                            _addressListResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 403 || it.code() == 400) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _addressListResponse.postValue(Resource.error(jsonObj.getJSONArray("errors")[0].toString()))

                            Log.d("TAG", "loginPhone: ${it.code()}")
                            _addressListResponse.postValue(Resource.error(jsonObj.getJSONArray("errors")[0].toString()))
                            _addressListResponse.postValue(Resource.error(it.message(), null))
                        } else {
                            _addressListResponse.postValue(
                                Resource.error(
                                    "Some thing went wrong", null
                                )
                            )
                        }
                    }
                } catch (e: Exception) {
                    _addressListResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _addressListResponse.postValue(Resource.error("No internet connection", null))
        }
    }

}
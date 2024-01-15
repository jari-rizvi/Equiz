package com.teamx.equiz.ui.fragments.address

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.teamx.equiz.baseclasses.BaseViewModel
import com.teamx.equiz.data.models.addressbyid.GetAddressById
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.data.remote.reporitory.MainRepository
import com.teamx.equiz.ui.fragments.address.dataclasses.AddressOrderCreate
import com.teamx.equiz.ui.fragments.address.dataclasses.getAddressList.GetAddressListData
import com.teamx.equiz.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {


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


    private val _addAddressResponse = MutableLiveData<Resource<GetAddressListData>>()
    val addAddressResponse: LiveData<Resource<GetAddressListData>>
        get() = _addAddressResponse

    fun addAddress(param: JsonObject) {
        viewModelScope.launch {
            _addAddressResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.addAddress(param).let {
                        if (it.isSuccessful) {
                            _addAddressResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 401) {
//                            unAuthorizedCallback.onToSignUpPage()
                            _addAddressResponse.postValue(Resource.error(it.message(), null))
                        } else if (it.code() == 401) {
                            _addAddressResponse.postValue(Resource.unAuth("", null))
                        } else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
//                            _addAddressResponse.postValue(Resource.error(it.message(), null))
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _addAddressResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _addAddressResponse.postValue(
                                Resource.error(
                                    "Some thing went wrong",
                                    null
                                )
                            )
                        }
                    }
                } catch (e: Exception) {
                    _addAddressResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _addAddressResponse.postValue(Resource.error("No internet connection", null))
        }
    }


    private val _deleteAddressResponse = MutableLiveData<Resource<GetAddressListData>>()
    val deleteAddressResponse: LiveData<Resource<GetAddressListData>>
        get() = _deleteAddressResponse

    fun deleteAddress(id: String) {
        viewModelScope.launch {
            _deleteAddressResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.deleteAddress(id).let {
                        if (it.isSuccessful) {
                            _deleteAddressResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 401) {
//                            unAuthorizedCallback.onToSignUpPage()
                            _deleteAddressResponse.postValue(Resource.error(it.message(), null))
                        } else if (it.code() == 401) {
                            _deleteAddressResponse.postValue(Resource.unAuth("", null))
                        } else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
//                            _deleteAddressResponse.postValue(Resource.error(it.message(), null))
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _deleteAddressResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _deleteAddressResponse.postValue(
                                Resource.error(
                                    "Some thing went wrong",
                                    null
                                )
                            )
                        }
                    }
                } catch (e: Exception) {
                    _deleteAddressResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _deleteAddressResponse.postValue(Resource.error("No internet connection", null))
        }
    }


    private val _updateAddressResponse = MutableLiveData<Resource<GetAddressById>>()
    val updateAddressResponse: LiveData<Resource<GetAddressById>>
        get() = _updateAddressResponse

    fun updateAddress(param: JsonObject) {
        viewModelScope.launch {
            _updateAddressResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.updateAddress(param).let {
                        if (it.isSuccessful) {
                            _updateAddressResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 401) {
//                            unAuthorizedCallback.onToSignUpPage()
                            _updateAddressResponse.postValue(Resource.error(it.message(), null))
                        } else if (it.code() == 401) {
                            _updateAddressResponse.postValue(Resource.unAuth("", null))
                        } else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
//                            _updateAddressResponse.postValue(Resource.error(it.message(), null))
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _updateAddressResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _updateAddressResponse.postValue(
                                Resource.error(
                                    "Some thing went wrong",
                                    null
                                )
                            )
                        }
                    }
                } catch (e: Exception) {
                    _updateAddressResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _updateAddressResponse.postValue(Resource.error("No internet connection", null))
        }
    }


    private val _addresByIdResponse = MutableLiveData<Resource<GetAddressById>>()
    val addresByIdResponse: LiveData<Resource<GetAddressById>>
        get() = _addresByIdResponse

    fun addresById(id: String) {
        viewModelScope.launch {
            _addresByIdResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.getAddressById(id).let {
                        if (it.isSuccessful) {
                            _addresByIdResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 401) {
//                            unAuthorizedCallback.onToSignUpPage()
                            _addresByIdResponse.postValue(Resource.error(it.message(), null))
                        } else if (it.code() == 401) {
                            _addresByIdResponse.postValue(Resource.unAuth("", null))
                        } else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
//                            _addresByIdResponse.postValue(Resource.error(it.message(), null))
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _addresByIdResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _addresByIdResponse.postValue(
                                Resource.error(
                                    "Some thing went wrong",
                                    null
                                )
                            )
                        }
                    }
                } catch (e: Exception) {
                    _addresByIdResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _addresByIdResponse.postValue(Resource.error("No internet connection", null))
        }
    }


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
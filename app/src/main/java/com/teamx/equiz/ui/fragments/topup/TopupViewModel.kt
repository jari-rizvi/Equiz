package com.teamx.equiz.ui.fragments.topup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.teamx.equiz.baseclasses.BaseViewModel
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.data.remote.reporitory.MainRepository
import com.teamx.equiz.ui.fragments.ecommerce.paymentMethods.model.StripeModel
import com.teamx.equiz.ui.fragments.topup.data.TopUpModelData
import com.teamx.equiz.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TopupViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {


   /* private val _stripeData = MutableLiveData<Resource<StripeModel>>()
    val stripeData: LiveData<Resource<StripeModel>>
        get() = _stripeData

    fun stripeDataMethod(param: JsonObject) {
        Timber.tag("1235").d("StripeData:1 $param")
        viewModelScope.launch {
            _stripeData.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.stripeDataMethod(param).let {
                        if (it.isSuccessful) {
                            Timber.tag("1235").d("StripeData:2 ")
                            _stripeData.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 401) {
                            _addressUpdateResponse.postValue(Resource.unAuth("", null))
                        } else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
                            Timber.tag("1235").d("StripeData:2.3")
                            Timber.tag("1235").d("StripeData:2.3${it.errorBody().toString()}")

//                            _stripeData.postValue(
//                                Resource.error(
//                                    it.message() + it.code() + it.errorBody(),
//                                    null
//                                )
//                            )
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            Timber.tag("1235")
                                .d("StripeData:2.3${jsonObj.getString("message")}")
                            _stripeData.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            Timber.tag("1235").d("StripeData:33")
                            Timber.tag("1235").d("StripeData:33${it.code()}")
                            _stripeData.postValue(
                                Resource.error(
                                    "Some thing went wrong${it.message()}", it.body()
                                )
                            )
                        }
                    }

                } catch (e: Exception) {
                    Timber.tag("1235").d("StripeData:4 ")
                    _stripeData.postValue(Resource.error("${e.message}"))
                }
            } else _stripeData.postValue(Resource.error("No internet connection", null))
        }
    }*/


    private val _addTopResponse = MutableLiveData<Resource<TopUpModelData>>()
    val addTopResponse: LiveData<Resource<TopUpModelData>>
        get() = _addTopResponse

    fun addTop(param: JsonObject) {
        viewModelScope.launch {
            _addTopResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.addTopUp(param).let {
                        if (it.isSuccessful) {
                            _addTopResponse.postValue(Resource.success(it.body()!!))
                        }  else if (it.code() == 401) {
                            _addTopResponse.postValue(Resource.unAuth("", null))
                        } else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
//                            _addTopResponse.postValue(Resource.error(it.message(), null))
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _addTopResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _addTopResponse.postValue(
                                Resource.error(
                                    "Some thing went wrong",
                                    null
                                )
                            )
                        }
                    }
                } catch (e: Exception) {
                    _addTopResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _addTopResponse.postValue(Resource.error("No internet connection", null))
        }
    }


}
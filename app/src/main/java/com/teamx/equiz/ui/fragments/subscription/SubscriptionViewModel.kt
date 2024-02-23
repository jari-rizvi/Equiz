package com.teamx.equiz.ui.fragments.subscription

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.teamx.equiz.baseclasses.BaseViewModel
import com.teamx.equiz.data.models.getPlan.GerPlanData
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.data.remote.reporitory.MainRepository
import com.teamx.equiz.ui.fragments.ecommerce.productProfile.unsub_data.UNSUBDataModel
import com.teamx.equiz.ui.fragments.subscription.data.SubData
import com.teamx.equiz.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SubscriptionViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {


    private val _unsubResponse = MutableLiveData<Resource<UNSUBDataModel>>()
    val unsubResponse: LiveData<Resource<UNSUBDataModel>>
        get() = _unsubResponse

    fun unsub() {
        viewModelScope.launch {
            _unsubResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.unsub().let {
                        if (it.isSuccessful) {
                            _unsubResponse.postValue(Resource.success(it.body()!!))
                        }
                        /*  else if (it.code() == 401) {
                              _unsubResponse.postValue(Resource.unAuth("", null))
                          }*/ else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _unsubResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _unsubResponse.postValue(Resource.error(jsonObj.getString("message")))
//                            _meResponse.postValue(Resource.error(it.message(), null))
                        }
                    }
                } catch (e: Exception) {
                    _unsubResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _unsubResponse.postValue(Resource.error("No internet connection", null))
        }
    }

    private val _getPlanResponse = MutableLiveData<Resource<GerPlanData>>()
    val getPlanResponse: LiveData<Resource<GerPlanData>>
        get() = _getPlanResponse

    fun getPlan() {
        viewModelScope.launch {
            _getPlanResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    Timber.tag("87878787887").d("starta")

                    mainRepository.getPlan().let {
                        if (it.isSuccessful) {
                            _getPlanResponse.postValue(Resource.success(it.body()!!))
                            Timber.tag("87878787887").d(it.body()!!.toString())
                        } /*else if (it.code() == 401) {
                            _getPlanResponse.postValue(Resource.unAuth("", null))
                        }*/ else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
                            Timber.tag("87878787887").d("secoonnddd")

//                            _getPlanResponse.postValue(Resource.error(it.message(), null))
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _getPlanResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _getPlanResponse.postValue(
                                Resource.error(
                                    "Some thing went wrong",
                                    null
                                )
                            )
                            Timber.tag("87878787887").d("third")

                        }
                    }
                } catch (e: Exception) {
                    _getPlanResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _getPlanResponse.postValue(
                Resource.error(
                    "No internet connection",
                    null
                )
            )
        }
    }

    private val _subPlanResponse = MutableLiveData<Resource<SubData>>()
    val subPlanResponse: LiveData<Resource<SubData>>
        get() = _subPlanResponse

    fun subPlan(updateCart: JsonObject) {
        viewModelScope.launch {
            _subPlanResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    Timber.tag("87878787887").d("starta")

                    mainRepository.subPlan(updateCart).let {
                        if (it.isSuccessful) {
                            _subPlanResponse.postValue(Resource.success(it.body()!!))
                            Timber.tag("87878787887").d(it.body()!!.toString())
                        } /*else if (it.code() == 401) {
                            _subPlanResponse.postValue(Resource.unAuth("", null))
                        }*/ else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
                            Timber.tag("87878787887").d("secoonnddd")

//                            _subPlanResponse.postValue(Resource.error(it.message(), null))
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _subPlanResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _subPlanResponse.postValue(
                                Resource.error(
                                    "Some thing went wrong",
                                    null
                                )
                            )
                            Timber.tag("87878787887").d("third")

                        }
                    }
                } catch (e: Exception) {
                    _subPlanResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _subPlanResponse.postValue(
                Resource.error(
                    "No internet connection",
                    null
                )
            )
        }
    }

}
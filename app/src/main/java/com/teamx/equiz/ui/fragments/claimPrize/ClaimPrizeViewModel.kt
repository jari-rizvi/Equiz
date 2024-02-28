package com.teamx.equiz.ui.fragments.claimPrize

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.teamx.equiz.baseclasses.BaseViewModel
import com.teamx.equiz.data.models.getPlan.GerPlanData
import com.teamx.equiz.data.models.scratchData.ScratchImgData
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.data.remote.reporitory.MainRepository
import com.teamx.equiz.ui.fragments.claimPrize.model.ClaimPrizeModel
import com.teamx.equiz.ui.fragments.collectPrice.data.CollectDataModel
import com.teamx.equiz.ui.fragments.ecommerce.productProfile.unsub_data.UNSUBDataModel
import com.teamx.equiz.ui.fragments.subscription.data.SubData
import com.teamx.equiz.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ClaimPrizeViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {

    private val _claimPrizeResponse = MutableLiveData<Resource<ClaimPrizeModel>>()
    val claimPrizeResponse: LiveData<Resource<ClaimPrizeModel>>
        get() = _claimPrizeResponse

    fun claimPrize(id:String) {
        viewModelScope.launch {
            _claimPrizeResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.claimPrize(id).let {
                        if (it.isSuccessful) {
                            _claimPrizeResponse.postValue(Resource.success(it.body()!!))
                        }
                        /*  else if (it.code() == 401) {
                              _claimPrizeResponse.postValue(Resource.unAuth("", null))
                          }*/else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _claimPrizeResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _claimPrizeResponse.postValue(Resource.error(jsonObj.getString("message")))
//                            _claimPrizeResponse.postValue(Resource.error(it.message(), null))
                        }
                    }
                } catch (e: Exception) {
                    _claimPrizeResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _claimPrizeResponse.postValue(Resource.error("No internet connection", null))
        }
    }

    private val _scratchimgResponse = MutableLiveData<Resource<ScratchImgData>>()
    val scratchimgResponse: LiveData<Resource<ScratchImgData>>
        get() = _scratchimgResponse

    fun scratchimg(id: String) {
        viewModelScope.launch {
            _scratchimgResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.GetScratchImg(id).let {
                        if (it.isSuccessful) {
                            _scratchimgResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 401) {
//                            unAuthorizedCallback.onToSignUpPage()
                            _scratchimgResponse.postValue(Resource.error(it.message(), null))
                        } else if (it.code() == 401) {
                            _scratchimgResponse.postValue(Resource.unAuth("", null))
                        } else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
//                            _scratchimgResponse.postValue(Resource.error(it.message(), null))
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _scratchimgResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _scratchimgResponse.postValue(
                                Resource.error(
                                    "Some thing went wrong",
                                    null
                                )
                            )
                        }
                    }
                } catch (e: Exception) {
                    _scratchimgResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _scratchimgResponse.postValue(Resource.error("No internet connection", null))
        }
    }


    private val _submitClaimResponse = MutableLiveData<Resource<ScratchImgData>>()
    val submitClaimResponse: LiveData<Resource<ScratchImgData>>
        get() = _submitClaimResponse

    fun submitClaim(params: JsonObject?) {
        viewModelScope.launch {
            _submitClaimResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.submitClaim(params).let {
                        if (it.isSuccessful) {
                            _submitClaimResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 401) {
//                            unAuthorizedCallback.onToSignUpPage()
                            _submitClaimResponse.postValue(Resource.error(it.message(), null))
                        } else if (it.code() == 401) {
                            _submitClaimResponse.postValue(Resource.unAuth("", null))
                        } else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
//                            _submitClaimResponse.postValue(Resource.error(it.message(), null))
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _submitClaimResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _submitClaimResponse.postValue(
                                Resource.error(
                                    "Some thing went wrong",
                                    null
                                )
                            )
                        }
                    }
                } catch (e: Exception) {
                    _submitClaimResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _submitClaimResponse.postValue(Resource.error("No internet connection", null))
        }
    }


}
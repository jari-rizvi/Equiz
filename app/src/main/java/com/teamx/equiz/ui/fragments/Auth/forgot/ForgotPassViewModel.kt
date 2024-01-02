package com.teamx.equiz.ui.fragments.Auth.forgot


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.teamx.equiz.baseclasses.BaseViewModel
import com.teamx.equiz.data.models.forgotpassData.ForgotPassData
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.data.remote.reporitory.MainRepository
import com.teamx.equiz.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class ForgotPassViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {

    private val _forgotPassResponse = MutableLiveData<Resource<ForgotPassData>>()
    val forgotPassResponse: LiveData<Resource<ForgotPassData>>
        get() = _forgotPassResponse

    fun forgotPassEmail(param: JsonObject) {
        viewModelScope.launch {
            _forgotPassResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.forgotpass(param).let {
                        if (it.isSuccessful) {
                            _forgotPassResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 401) {

                            _forgotPassResponse.postValue(Resource.error(it.message(), null))
                        } else if (it.code() == 401) {
                            _forgotPassResponse.postValue(Resource.unAuth("", null))
                        } else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _forgotPassResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _forgotPassResponse.postValue(Resource.error("Some thing went wrong", null))
                        }
                    }
                } catch (e: Exception) {
                    _forgotPassResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _forgotPassResponse.postValue(Resource.error("No internet connection", null))
        }
    }
    fun forgotPassPhone(param: JsonObject) {
        viewModelScope.launch {
            _forgotPassResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.forgotpass(param).let {
                        if (it.isSuccessful) {
                            _forgotPassResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 401) {

                            _forgotPassResponse.postValue(Resource.error(it.message(), null))
                        } else if (it.code() == 401) {
                            _forgotPassResponse.postValue(Resource.unAuth("", null))
                        } else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _forgotPassResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _forgotPassResponse.postValue(Resource.error("Some thing went wrong", null))
                        }
                    }
                } catch (e: Exception) {
                    _forgotPassResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _forgotPassResponse.postValue(Resource.error("No internet connection", null))
        }
    }



}
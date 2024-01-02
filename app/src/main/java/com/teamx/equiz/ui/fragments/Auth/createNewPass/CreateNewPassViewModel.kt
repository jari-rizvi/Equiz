package com.teamx.equiz.ui.fragments.Auth.createNewPass


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.teamx.equiz.baseclasses.BaseViewModel
import com.teamx.equiz.data.models.sucessData.SuccessData
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.data.remote.reporitory.MainRepository
import com.teamx.equiz.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class CreateNewPassViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {


    private val _resetPasswordResponse = MutableLiveData<Resource<SuccessData>>()
    val resetPasswordResponse: LiveData<Resource<SuccessData>>
        get() = _resetPasswordResponse

    fun resetPassword(param: JsonObject) {
        viewModelScope.launch {
            _resetPasswordResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.resetPass(param).let {
                        if (it.isSuccessful) {
                            _resetPasswordResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 401) {
//                            unAuthorizedCallback.onToSignUpPage()
                            _resetPasswordResponse.postValue(Resource.error(it.message(), null))
                        } else if (it.code() == 401) {
                            _resetPasswordResponse.postValue(Resource.unAuth("", null))
                        } else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
//                            _resetPasswordResponse.postValue(Resource.error(it.message(), null))
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _resetPasswordResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _resetPasswordResponse.postValue(
                                Resource.error(
                                    "Some thing went wrong",
                                    null
                                )
                            )
                        }
                    }
                } catch (e: Exception) {
                    _resetPasswordResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _resetPasswordResponse.postValue(Resource.error("No internet connection", null))
        }
    }


    private val _changePasswordResponse = MutableLiveData<Resource<SuccessData>>()
    val changePasswordResponse: LiveData<Resource<SuccessData>>
        get() = _changePasswordResponse

    fun changePassword(param: JsonObject) {
        viewModelScope.launch {
            _changePasswordResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.changePass(param).let {
                        if (it.isSuccessful) {
                            _changePasswordResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 401) {
//                            unAuthorizedCallback.onToSignUpPage()
                            _changePasswordResponse.postValue(Resource.error(it.message(), null))
                        } else if (it.code() == 401) {
                            _changePasswordResponse.postValue(Resource.unAuth("", null))
                        } else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
//                            _changePasswordResponse.postValue(Resource.error(it.message(), null))
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _changePasswordResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _changePasswordResponse.postValue(
                                Resource.error(
                                    "Some thing went wrong",
                                    null
                                )
                            )
                        }
                    }
                } catch (e: Exception) {
                    _changePasswordResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _changePasswordResponse.postValue(Resource.error("No internet connection", null))
        }
    }


}
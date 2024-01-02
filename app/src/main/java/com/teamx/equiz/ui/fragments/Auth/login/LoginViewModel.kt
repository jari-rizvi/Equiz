package com.teamx.equiz.ui.fragments.Auth.login


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.teamx.equiz.baseclasses.BaseViewModel
import com.teamx.equiz.data.models.loginData.LoginData
import com.teamx.equiz.data.models.meModel.MeModel
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.data.remote.reporitory.MainRepository
import com.teamx.equiz.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {


    private val _loginResponse = MutableLiveData<Resource<LoginData>>()
    val loginResponse: LiveData<Resource<LoginData>>
        get() = _loginResponse

    fun loginPhone(param: JsonObject/*, unAuthorizedCallback: UnAuthorizedCallback*/) {
        viewModelScope.launch {
            _loginResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.loginPhone(param).let {
                        if (it.isSuccessful) {
                            _loginResponse.postValue(Resource.success(it.body()!!))
                        }  else if (it.code() == 302) {
                            _loginResponse.postValue(Resource.notVerify("", null))
                        } else if (it.code() == 401) {
                            _loginResponse.postValue(Resource.unAuth("", null))
                        } else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400 || it.code() == 401) {
//                            _loginResponse.postValue(Resource.error(it.message(), null))
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _loginResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _loginResponse.postValue(Resource.error("Some thing went wrong", null))
                        }
                    }
                } catch (e: Exception) {
                    _loginResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _loginResponse.postValue(Resource.error("No internet connection", null))
        }
    }



    private val _meResponse = MutableLiveData<Resource<MeModel>>()
    val meResponse: LiveData<Resource<MeModel>>
        get() = _meResponse
    fun me() {
        viewModelScope.launch {
            _meResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.me().let {
                        if (it.isSuccessful) {
                            _meResponse.postValue(Resource.success(it.body()!!))
                        }
                        /*  else if (it.code() == 401) {
                              _meResponse.postValue(Resource.unAuth("", null))
                          }*/else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _meResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _meResponse.postValue(Resource.error(jsonObj.getString("message")))
//                            _meResponse.postValue(Resource.error(it.message(), null))
                        }
                    }
                } catch (e: Exception) {
                    _meResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _meResponse.postValue(Resource.error("No internet connection", null))
        }
    }

}
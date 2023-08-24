package com.teamx.equiz.ui.fragments.Auth.signup


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.teamx.equiz.baseclasses.BaseViewModel
import com.teamx.equiz.data.models.signupData.SignupData
import com.teamx.equiz.data.models.sucessData.SuccessData
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.data.remote.reporitory.MainRepository
import com.teamx.equiz.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {

    private val _signupResponse = MutableLiveData<Resource<SignupData>>()
    val signupResponse: LiveData<Resource<SignupData>>
        get() = _signupResponse

    fun signup(param: JsonObject/*, unAuthorizedCallback: UnAuthorizedCallback*/) {
        viewModelScope.launch {
            _signupResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.Signup(param).let {
                        if (it.isSuccessful) {
                            _signupResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 401) {
//                            unAuthorizedCallback.onToSignUpPage()
                            _signupResponse.postValue(Resource.error(it.message(), null))
                        } else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
//                            _signupResponse.postValue(Resource.error(it.message(), null))
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _signupResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _signupResponse.postValue(Resource.error("Some thing went wrong", null))
                        }
                    }
                } catch (e: Exception) {
                    _signupResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _signupResponse.postValue(Resource.error("No internet connection", null))
        }
    }


}
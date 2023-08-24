package com.teamx.equiz.ui.fragments.Auth.otp



import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.teamx.equiz.baseclasses.BaseViewModel
import com.teamx.equiz.data.models.loginData.LoginData
import com.teamx.equiz.data.models.otpForgotData.OtpforgotData
import com.teamx.equiz.data.models.sucessData.SuccessData
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.data.remote.reporitory.MainRepository
import com.teamx.equiz.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class OtpViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {


    private val _otpVerifyForgotResponse = MutableLiveData<Resource<OtpforgotData>>()
    val otpVerifyForgotResponse: LiveData<Resource<OtpforgotData>>
        get() = _otpVerifyForgotResponse

    private val _otpVerifyResponse = MutableLiveData<Resource<LoginData>>()
    val otpVerifyResponse: LiveData<Resource<LoginData>>
        get() = _otpVerifyResponse


    fun otpVerify(uniqueID: String) {
        viewModelScope.launch {
            _otpVerifyResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    Timber.tag("87878787887").d( "starta")

                    mainRepository.otpVerify(uniqueID).let {
                        if (it.isSuccessful) {
                            _otpVerifyResponse.postValue(Resource.success(it.body()!!))
                            Timber.tag("87878787887").d( it.body()!!.toString())
                        }  else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
                            Timber.tag("87878787887").d( "secoonnddd")

//                            _otpVerifyResponse.postValue(Resource.error(it.message(), null))
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _otpVerifyResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _otpVerifyResponse.postValue(
                                Resource.error(
                                    "Some thing went wrong",
                                    null
                                )
                            )
                            Timber.tag("87878787887").d( "third")

                        }
                    }
                } catch (e: Exception) {
                    _otpVerifyResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _otpVerifyResponse.postValue(Resource.error("No internet connection", null))
        }
    }
    fun otpVerifyForgot(uniqueID: String) {
        viewModelScope.launch {
            _otpVerifyForgotResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    Timber.tag("87878787887").d( "starta")

                    mainRepository.otpVerifyForgot(uniqueID).let {
                        if (it.isSuccessful) {
                            _otpVerifyForgotResponse.postValue(Resource.success(it.body()!!))
                            Timber.tag("87878787887").d( it.body()!!.toString())
                        }  else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
                            Timber.tag("87878787887").d( "secoonnddd")

//                            _otpVerifyForgotResponse.postValue(Resource.error(it.message(), null))
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _otpVerifyForgotResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _otpVerifyForgotResponse.postValue(
                                Resource.error(
                                    "Some thing went wrong",
                                    null
                                )
                            )
                            Timber.tag("87878787887").d( "third")

                        }
                    }
                } catch (e: Exception) {
                    _otpVerifyForgotResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _otpVerifyForgotResponse.postValue(Resource.error("No internet connection", null))
        }
    }

}
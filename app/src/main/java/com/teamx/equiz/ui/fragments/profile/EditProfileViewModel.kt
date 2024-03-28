package com.teamx.equiz.ui.fragments.profile


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.teamx.equiz.baseclasses.BaseViewModel
import com.teamx.equiz.data.models.ResendOtpData
import com.teamx.equiz.data.models.Successs
import com.teamx.equiz.data.models.bankData.BankData
import com.teamx.equiz.data.models.editProfile.EditProfileData
import com.teamx.equiz.data.models.meModel.MeModel
import com.teamx.equiz.data.models.modelUploadImages.ModelUploadImage
import com.teamx.equiz.data.models.uploadImgDoc.UploadImgDoc
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.data.remote.reporitory.MainRepository
import com.teamx.equiz.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {


    private val _resendOtpPRofileResponse = MutableLiveData<Resource<ResendOtpData>>()
    val resendOtpPRofileResponse: LiveData<Resource<ResendOtpData>>
        get() = _resendOtpPRofileResponse

    fun resendOtpProfile(param: JsonObject) {
        viewModelScope.launch {
            _resendOtpPRofileResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.resendOtpProfile(param).let {
                        if (it.isSuccessful) {
                            _resendOtpPRofileResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 401) {

                            _resendOtpPRofileResponse.postValue(Resource.error(it.message(), null))
                        } else if (it.code() == 401) {
                            _resendOtpPRofileResponse.postValue(Resource.unAuth("", null))
                        } else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _resendOtpPRofileResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _resendOtpPRofileResponse.postValue(
                                Resource.error(
                                    "Some thing went wrong",
                                    null
                                )
                            )
                        }
                    }
                } catch (e: Exception) {
                    _resendOtpPRofileResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _resendOtpPRofileResponse.postValue(Resource.error("No internet connection", null))
        }
    }


    private val _updateProfileResponse = MutableLiveData<Resource<EditProfileData>>()
    val updateProfileResponse: LiveData<Resource<EditProfileData>>
        get() = _updateProfileResponse

    fun updateProfile(params: JsonObject) {
        viewModelScope.launch {
            _updateProfileResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.updateProfile(params).let {
                        if (it.isSuccessful) {
                            _updateProfileResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 401) {
                            _updateProfileResponse.postValue(Resource.unAuth("", null))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _updateProfileResponse.postValue(Resource.error(jsonObj.getString("message")))
                            Log.d(
                                "uploadReviewImg",
                                "jsonObj ${it.code()}: ${jsonObj.getString("message")}"
                            )
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _updateProfileResponse.postValue(Resource.error(jsonObj.getString("message")))
                            Log.d("uploadReviewImg", "jsonObj: ${jsonObj.getString("message")}")
                        }
                    }
                } catch (e: Exception) {
                    Log.d("uploadReviewImg", "Exception: ${e.message}")
                    _updateProfileResponse.postValue(Resource.error("${e.message}", null))
                }
            } else {
                _updateProfileResponse.postValue(Resource.error("No internet connection", null))
            }
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
                          }*/ else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
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


    private val _uploadReviewImgResponse = MutableLiveData<Resource<ModelUploadImage>>()
    val uploadReviewImgResponse: LiveData<Resource<ModelUploadImage>>
        get() = _uploadReviewImgResponse

    fun uploadReviewImg(imageParts: List<MultipartBody.Part>) {
        viewModelScope.launch {
            _uploadReviewImgResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.uploadReviewImg(imageParts).let {
                        if (it.isSuccessful) {
                            _uploadReviewImgResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 401) {
                            _uploadReviewImgResponse.postValue(Resource.unAuth("", null))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _uploadReviewImgResponse.postValue(Resource.error(jsonObj.getString("message")))
                            Log.d(
                                "uploadReviewImg",
                                "jsonObj ${it.code()}: ${jsonObj.getString("message")}"
                            )
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _uploadReviewImgResponse.postValue(Resource.error(jsonObj.getString("message")))
                            Log.d("uploadReviewImg", "jsonObj: ${jsonObj.getString("message")}")
                        }
                    }
                } catch (e: Exception) {
                    Log.d("uploadReviewImg", "Exception: ${e.message}")
                    _uploadReviewImgResponse.postValue(Resource.error("${e.message}", null))
                }
            } else {
                _uploadReviewImgResponse.postValue(Resource.error("No internet connection", null))
            }
        }
    }


    private val _uploadDocImgResponse = MutableLiveData<Resource<UploadImgDoc>>()
    val uploadDocImgResponse: LiveData<Resource<UploadImgDoc>>
        get() = _uploadDocImgResponse

    fun uploadDocImg(imageParts: List<MultipartBody.Part>) {
        viewModelScope.launch {
            _uploadDocImgResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.uploadDocImg(imageParts).let {
                        if (it.isSuccessful) {
                            _uploadDocImgResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 401) {
                            _uploadDocImgResponse.postValue(Resource.unAuth("", null))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _uploadDocImgResponse.postValue(Resource.error(jsonObj.getString("message")))
                            Log.d(
                                "uploadDocImg",
                                "jsonObj ${it.code()}: ${jsonObj.getString("message")}"
                            )
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _uploadDocImgResponse.postValue(Resource.error(jsonObj.getString("message")))
                            Log.d("uploadDocImg", "jsonObj: ${jsonObj.getString("message")}")
                        }
                    }
                } catch (e: Exception) {
                    Log.d("uploadDocImg", "Exception: ${e.message}")
                    _uploadDocImgResponse.postValue(Resource.error("${e.message}", null))
                }
            } else {
                _uploadDocImgResponse.postValue(Resource.error("No internet connection", null))
            }
        }
    }


    private val _bankDetailsResponse = MutableLiveData<Resource<BankData>>()
    val bankDetailsResponse: LiveData<Resource<BankData>>
        get() = _bankDetailsResponse

    fun bankDetails(param: JsonObject) {
        viewModelScope.launch {
            _bankDetailsResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.bankDetails(param).let {
                        if (it.isSuccessful) {
                            _bankDetailsResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 401) {

                            _bankDetailsResponse.postValue(Resource.error(it.message(), null))
                        } else if (it.code() == 401) {
                            _bankDetailsResponse.postValue(Resource.unAuth("", null))
                        } else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _bankDetailsResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _bankDetailsResponse.postValue(
                                Resource.error(
                                    "Some thing went wrong",
                                    null
                                )
                            )
                        }
                    }
                } catch (e: Exception) {
                    _bankDetailsResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _bankDetailsResponse.postValue(Resource.error("No internet connection", null))
        }
    }


    private val _updateSocialsResponse = MutableLiveData<Resource<Successs>>()
    val updateSocialsResponse: LiveData<Resource<Successs>>
        get() = _updateSocialsResponse

    fun updateSocials(param: JsonObject) {
        viewModelScope.launch {
            _updateSocialsResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.updateSocials(param).let {
                        if (it.isSuccessful) {
                            _updateSocialsResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 401) {

                            _updateSocialsResponse.postValue(Resource.error(it.message(), null))
                        } else if (it.code() == 401) {
                            _updateSocialsResponse.postValue(Resource.unAuth("", null))
                        } else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _updateSocialsResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _updateSocialsResponse.postValue(
                                Resource.error(
                                    "Some thing went wrong",
                                    null
                                )
                            )
                        }
                    }
                } catch (e: Exception) {
                    _updateSocialsResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _updateSocialsResponse.postValue(Resource.error("No internet connection", null))
        }
    }



}
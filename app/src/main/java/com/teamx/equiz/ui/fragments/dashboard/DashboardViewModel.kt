package com.teamx.equiz.ui.fragments.dashboard


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.teamx.equiz.baseclasses.BaseViewModel
import com.teamx.equiz.data.models.bannerData.bannews.BanNews
import com.teamx.equiz.data.models.getwalletData.GetWalletData
import com.teamx.equiz.data.models.meModel.MeModel
import com.teamx.equiz.data.models.quizTitleData.QuizTitleData
import com.teamx.equiz.data.models.topWinnerData.TopWinnerData
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.data.remote.reporitory.MainRepository
import com.teamx.equiz.ui.fragments.Auth.datanotify.DataFCMModel
import com.teamx.equiz.utils.NetworkHelper
import com.teamx.equiz.utils.UnAuthorizedCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {


    private val _getquizTitileResponse = MutableLiveData<Resource<QuizTitleData>>()
    val getquizTitileResponse: LiveData<Resource<QuizTitleData>>
        get() = _getquizTitileResponse

    fun getquizTitile(
        country: String, topic: String, type: String,
        unAuthorizedCallback: UnAuthorizedCallback
    ) {
        viewModelScope.launch {
            _getquizTitileResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    Timber.tag("87878787887").d("starta")

                    mainRepository.getQuizTitle(country, topic, type).let {
                        if (it.isSuccessful) {
                            _getquizTitileResponse.postValue(Resource.success(it.body()!!))
                            Timber.tag("87878787887").d(it.body()!!.toString())
                        } else if (it.code() == 401) {
                            _getquizTitileResponse.postValue(Resource.unAuth("", null))
                        } else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
                            Timber.tag("87878787887").d("secoonnddd")

//                            _getquizTitileResponse.postValue(Resource.error(it.message(), null))
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _getquizTitileResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _getquizTitileResponse.postValue(
                                Resource.error(
                                    "Some thing went wrong",
                                    null
                                )
                            )
                            Timber.tag("87878787887").d("third")

                        }
                    }
                } catch (e: Exception) {
                    _getquizTitileResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _getquizTitileResponse.postValue(Resource.error("No internet connection", null))
        }
    }


    private val _getwalletResponse = MutableLiveData<Resource<GetWalletData>>()
    val getwalletResponse: LiveData<Resource<GetWalletData>>
        get() = _getwalletResponse

    fun getWallet(
        unAuthorizedCallback: UnAuthorizedCallback
    ) {
        viewModelScope.launch {
            _getwalletResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    Timber.tag("87878787887").d("starta")

                    mainRepository.getWallet().let {
                        if (it.isSuccessful) {
                            _getwalletResponse.postValue(Resource.success(it.body()!!))
                            Timber.tag("87878787887").d(it.body()!!.toString())
                        } else if (it.code() == 401) {
                            _getwalletResponse.postValue(Resource.unAuth("", null))
                        } else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
                            Timber.tag("87878787887").d("secoonnddd")

//                            _getwalletResponse.postValue(Resource.error(it.message(), null))
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _getwalletResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _getwalletResponse.postValue(
                                Resource.error(
                                    "Some thing went wrong",
                                    null
                                )
                            )
                            Timber.tag("87878787887").d("third")

                        }
                    }
                } catch (e: Exception) {
                    _getwalletResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _getwalletResponse.postValue(Resource.error("No internet connection", null))
        }
    }


    private val _getTopWinnersResponse = MutableLiveData<Resource<TopWinnerData>>()
    val getTopWinnersResponse: LiveData<Resource<TopWinnerData>>
        get() = _getTopWinnersResponse

    fun getTopWinners(
        unAuthorizedCallback: UnAuthorizedCallback
    ) {
        viewModelScope.launch {
            _getTopWinnersResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    Timber.tag("87878787887").d("starta")

                    mainRepository.getTopWinners().let {
                        if (it.isSuccessful) {
                            _getTopWinnersResponse.postValue(Resource.success(it.body()!!))
                            Timber.tag("87878787887").d(it.body()!!.toString())
                        } else if (it.code() == 401) {
                            _getTopWinnersResponse.postValue(Resource.unAuth("", null))
                        } else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
                            Timber.tag("87878787887").d("secoonnddd")

//                            _getTopWinnersResponse.postValue(Resource.error(it.message(), null))
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _getTopWinnersResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _getTopWinnersResponse.postValue(
                                Resource.error(
                                    "Some thing went wrong",
                                    null
                                )
                            )
                            Timber.tag("87878787887").d("third")

                        }
                    }
                } catch (e: Exception) {
                    _getTopWinnersResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _getTopWinnersResponse.postValue(Resource.error("No internet connection", null))
        }
    }





    private val _getBannerResponse = MutableLiveData<Resource<BanNews>>()
    val getBannerResponse: LiveData<Resource<BanNews>>
        get() = _getBannerResponse

    fun getBanners(
        params: JsonObject,
    ) {
        viewModelScope.launch {
            _getBannerResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    Timber.tag("87878787887").d("starta")

                    mainRepository.getBanners(params/*true*/).let {
                        if (it.isSuccessful) {
                            _getBannerResponse.postValue(Resource.success(it.body()!!))
                            Timber.tag("87878787887").d(it.body()!!.toString())
                        } else if (it.code() == 401) {
                            _getBannerResponse.postValue(Resource.unAuth("", null))
                        } else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
                            Timber.tag("87878787887").d("secoonnddd")

//                            _getBannerResponse.postValue(Resource.error(it.message(), null))
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _getBannerResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _getBannerResponse.postValue(
                                Resource.error(
                                    "Some thing went wrong",
                                    null
                                )
                            )
                            Timber.tag("87878787887").d("third")

                        }
                    }
                } catch (e: Exception) {
                    _getBannerResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _getBannerResponse.postValue(Resource.error("No internet connection", null))
        }
    }



  /*  private val _notifyfcmResponse = MutableLiveData<Resource<DataFCMModel>>()
    val notifyFcmResponse: LiveData<Resource<DataFCMModel>>
        get() = _notifyfcmResponse

    fun notifyFcms(
        params: JsonObject,
    ) {
        viewModelScope.launch {
            _notifyfcmResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    Timber.tag("87878787887").d("starta")

                    mainRepository.notifyFcm(params*//*true*//*).let {
                        if (it.isSuccessful) {
                            _notifyfcmResponse.postValue(Resource.success(it.body()!!))
                            Timber.tag("87878787887").d(it.body()!!.toString())
                        } else if (it.code() == 401) {
                            _notifyfcmResponse.postValue(Resource.unAuth("", null))
                        } else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
                            Timber.tag("87878787887").d("secoonnddd")

//                            _notifyfcmResponse.postValue(Resource.error(it.message(), null))
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _notifyfcmResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _notifyfcmResponse.postValue(
                                Resource.error(
                                    "Some thing went wrong",
                                    null
                                )
                            )
                            Timber.tag("87878787887").d("third")

                        }
                    }
                } catch (e: Exception) {
                    _notifyfcmResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _notifyfcmResponse.postValue(Resource.error("No internet connection", null))
        }
    }*/






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
                          else if (it.code() == 401) {
                              _meResponse.postValue(Resource.unAuth("", null))
                          }else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
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
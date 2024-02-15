package com.teamx.equiz.ui.fragments.wallet

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.teamx.equiz.baseclasses.BaseViewModel
import com.teamx.equiz.data.models.getwalletData.GetWalletData
import com.teamx.equiz.data.models.topWinnerData.TopWinnerData
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.data.remote.reporitory.MainRepository
import com.teamx.equiz.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.json.JSONObject
import timber.log.Timber
import java.io.ByteArrayInputStream
import java.io.InputStream
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {

    private val _getwalletResponse = MutableLiveData<Resource<GetWalletData>>()
    val getwalletResponse: LiveData<Resource<GetWalletData>>
        get() = _getwalletResponse

    fun getWallet() {
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


    private val _getTransResponse = MutableLiveData<Resource<ResponseBody>>()
    val getTransResponse: LiveData<Resource<ResponseBody>>
        get() = _getTransResponse

    fun getTrans(startData: String?, endDate: String?) {
        viewModelScope.launch {
            _getTransResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    Timber.tag("87878787887").d("starta")

                    mainRepository.getTransData(startData, endDate).let {
                        if (it.isSuccessful) {
                            _getTransResponse.postValue(Resource.success(it.body()!!))
                            Timber.tag("87878787887").d(it.body()!!.toString())
                        } else if (it.code() == 401) {
                            _getTransResponse.postValue(Resource.unAuth("", null))
                        } else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
                            Timber.tag("87878787887").d("secoonnddd")

//                            _getTransResponse.postValue(Resource.error(it.message(), null))
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _getTransResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _getTransResponse.postValue(
                                Resource.error(
                                    "Some thing went wrong",
                                    null
                                )
                            )
                            Timber.tag("87878787887").d("third")

                        }
                    }
                } catch (e: Exception) {
                    Log.d("getTransResponse", "dataRecive: ${e.message}")
                    _getTransResponse.postValue(Resource.error("${e.message}", null))
                }
            } else{
                _getwalletResponse.postValue(Resource.error("No internet connection", null))
            }
        }
    }
}
package com.teamx.equiz.ui.fragments.collectPrice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.teamx.equiz.baseclasses.BaseViewModel
import com.teamx.equiz.data.models.meModel.MeModel
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.data.remote.reporitory.MainRepository
import com.teamx.equiz.ui.fragments.collectPrice.data.CollectDataModel
import com.teamx.equiz.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class CollectPriceViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {



    private val _collectPrizeResponse = MutableLiveData<Resource<CollectDataModel>>()
    val collectPrizeResponse: LiveData<Resource<CollectDataModel>>
        get() = _collectPrizeResponse
    fun collectPrize() {
        viewModelScope.launch {
            _collectPrizeResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.collectPrizeRaffal().let {
                        if (it.isSuccessful) {
                            _collectPrizeResponse.postValue(Resource.success(it.body()!!))
                        }
                        /*  else if (it.code() == 401) {
                              _collectPrizeResponse.postValue(Resource.unAuth("", null))
                          }*/else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _collectPrizeResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _collectPrizeResponse.postValue(Resource.error(jsonObj.getString("message")))
//                            _collectPrizeResponse.postValue(Resource.error(it.message(), null))
                        }
                    }
                } catch (e: Exception) {
                    _collectPrizeResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _collectPrizeResponse.postValue(Resource.error("No internet connection", null))
        }
    }






}
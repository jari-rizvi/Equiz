package com.teamx.equiz.ui.game_fragments.game_random

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.teamx.equiz.baseclasses.BaseViewModel
import com.teamx.equiz.data.models.sucessData.gamesuccess.GameObj
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.data.remote.reporitory.MainRepository
import com.teamx.equiz.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class RandomGameFragsViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {


    private val resultResponseGame = MutableLiveData<Resource<GameObj>>()
    val resultResponseGameOB: LiveData<Resource<GameObj>>
        get() = resultResponseGame

    fun resultGame(param: JsonObject) {
        viewModelScope.launch {
            resultResponseGame.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.resultGame(param).let {
                        if (it.isSuccessful) {
                            resultResponseGame.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 401) {
                            resultResponseGame.postValue(Resource.unAuth("", null))
                        } else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
//                            _resetPasswordResponse.postValue(Resource.error(it.message(), null))
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            resultResponseGame.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            resultResponseGame.postValue(
                                Resource.error(
                                    "Some thing went wrong",
                                    null
                                )
                            )
                        }
                    }
                } catch (e: Exception) {
                    resultResponseGame.postValue(Resource.error("${e.message}", null))
                }
            } else resultResponseGame.postValue(Resource.error("No internet connection", null))
        }
    }





}
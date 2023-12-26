package com.teamx.equiz.ui.fragments.settings

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.teamx.equiz.baseclasses.BaseViewModel
import com.teamx.equiz.data.models.quizTitleData.QuizTitleData
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.data.remote.reporitory.MainRepository
import com.teamx.equiz.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {


    private val _quizTitleResponse = MutableLiveData<Resource<QuizTitleData>>()
    val quizTitleResponse: LiveData<Resource<QuizTitleData>>
        get() = _quizTitleResponse

    fun quizTitle(country: String,
                  topic: String?,
                  type: String,) {
        viewModelScope.launch {
            _quizTitleResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.quizTitle(country, topic, type).let {
                        if (it.isSuccessful) {
                            _quizTitleResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _quizTitleResponse.postValue(Resource.error(jsonObj.getString("message")))
                            Log.d("uploadReviewImg", "jsonObj ${it.code()}: ${jsonObj.getString("message")}")
                        }
                        /*else if (it.code() == 401) {
                            _quizTitleResponse.postValue(Resource.auth("", null))
                        } */
                        else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _quizTitleResponse.postValue(Resource.error(jsonObj.getString("message")))
                            Log.d("uploadReviewImg", "jsonObj: ${jsonObj.getString("message")}")
                        }
                    }
                } catch (e: Exception) {
                    Log.d("uploadReviewImg", "Exception: ${e.message}")
                    _quizTitleResponse.postValue(Resource.error("${e.message}", null))
                }
            } else{
                _quizTitleResponse.postValue(Resource.error("No internet connection", null))
            }
        }
    }


}
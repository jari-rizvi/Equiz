package com.teamx.equiz.ui.fragments.singlequize



import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.teamx.equiz.baseclasses.BaseViewModel
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.data.remote.reporitory.MainRepository
import com.teamx.equiz.ui.fragments.quizresult.data.QuizScoreData
import com.teamx.equiz.ui.fragments.singlequize.model.SingleQuizData
import com.teamx.equiz.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class SingleQuizesViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {


    private val _quizFindResponse = MutableLiveData<Resource<SingleQuizData>>()
    val quizFindResponse: LiveData<Resource<SingleQuizData>>
        get() = _quizFindResponse

    fun quizFind(
        id: String,
        topic: String?,
        type: String?
    ) {
        viewModelScope.launch {
            _quizFindResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.quizFind(id).let {
                        if (it.isSuccessful) {
                            _quizFindResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 401) {
                            _quizFindResponse.postValue(Resource.unAuth("", null))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _quizFindResponse.postValue(Resource.error(jsonObj.getString("message")))
                            Log.d(
                                "uploadReviewImg",
                                "jsonObj ${it.code()}: ${jsonObj.getString("message")}"
                            )
                        }
                        /*else if (it.code() == 401) {
                            _quizFindResponse.postValue(Resource.auth("", null))
                        } */
                        else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _quizFindResponse.postValue(Resource.error(jsonObj.getString("message")))
                            Log.d("uploadReviewImg", "jsonObj: ${jsonObj.getString("message")}")
                        }
                    }
                } catch (e: Exception) {
                    Log.d("uploadReviewImg", "Exception: ${e.message}")
                    _quizFindResponse.postValue(Resource.error("${e.message}", null))
                }
            } else {
                _quizFindResponse.postValue(Resource.error("No internet connection", null))
            }
        }
    }

    private val _quizResultResponse = MutableLiveData<Resource<QuizScoreData>>()
    val quizResultResponse: LiveData<Resource<QuizScoreData>>
        get() = _quizResultResponse

    fun quizResult(jsonObject: JsonObject) {
        viewModelScope.launch {
            _quizResultResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.quizResult(jsonObject).let {
                        if (it.isSuccessful) {
                            _quizResultResponse.postValue(Resource.success(it.body()!!))
                        } else if (it.code() == 401) {
                            _quizResultResponse.postValue(Resource.unAuth("", null))
                        } else if (it.code() == 500 || it.code() == 404 || it.code() == 400 || it.code() == 422) {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _quizResultResponse.postValue(Resource.error(jsonObj.getString("message")))
                            Log.d(
                                "uploadReviewImg",
                                "jsonObj ${it.code()}: ${jsonObj.getString("message")}"
                            )
                        }
                        /*else if (it.code() == 401) {
                            _quizResultResponse.postValue(Resource.auth("", null))
                        } */
                        else {
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _quizResultResponse.postValue(Resource.error(jsonObj.getString("message")))
                            Log.d("uploadReviewImg", "jsonObj: ${jsonObj.getString("message")}")
                        }
                    }
                } catch (e: Exception) {
                    Log.d("uploadReviewImg", "Exception: ${e.message}")
                    _quizResultResponse.postValue(Resource.error("${e.message}", null))
                }
            } else {
                _quizFindResponse.postValue(Resource.error("No internet connection", null))
            }
        }
    }

}
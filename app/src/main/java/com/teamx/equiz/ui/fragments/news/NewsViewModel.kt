package com.teamx.equiz.ui.fragments.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.teamx.equiz.baseclasses.BaseViewModel
import com.teamx.equiz.data.models.newsData.NewsData
import com.teamx.equiz.data.models.newsbyId.GetNewsByIdData
import com.teamx.equiz.data.models.sucessData.SuccessData
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.data.remote.reporitory.MainRepository
import com.teamx.equiz.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.checkerframework.checker.units.qual.Current
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {


    private val _getnewsdetailsResponse = MutableLiveData<Resource<GetNewsByIdData>>()
    val getnewsdetailsResponse: LiveData<Resource<GetNewsByIdData>>
        get() = _getnewsdetailsResponse


    fun getnewsdetails(id: String) {
        viewModelScope.launch {
            _getnewsdetailsResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    Timber.tag("87878787887").d( "starta")

                    mainRepository.getNewsById(id).let {
                        if (it.isSuccessful) {
                            _getnewsdetailsResponse.postValue(Resource.success(it.body()!!))
                            Timber.tag("87878787887").d( it.body()!!.toString())
                        }  else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
                            Timber.tag("87878787887").d( "secoonnddd")

//                            _getnewsdetailsResponse.postValue(Resource.error(it.message(), null))
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _getnewsdetailsResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _getnewsdetailsResponse.postValue(
                                Resource.error(
                                    "Some thing went wrong",
                                    null
                                )
                            )
                            Timber.tag("87878787887").d( "third")

                        }
                    }
                } catch (e: Exception) {
                    _getnewsdetailsResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _getnewsdetailsResponse.postValue(Resource.error("No internet connection", null))
        }
    }






    private val _getCurrentNewsResponse = MutableLiveData<Resource<NewsData>>()
    val getCurrentNewsResponse: LiveData<Resource<NewsData>>
        get() = _getCurrentNewsResponse

    fun getCurrentNews(current: Boolean) {
        viewModelScope.launch {
            _getCurrentNewsResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    Timber.tag("87878787887").d("starta")

                    mainRepository.getCurrentNews(current).let {
                        if (it.isSuccessful) {
                            _getCurrentNewsResponse.postValue(Resource.success(it.body()!!))
                            Timber.tag("87878787887").d(it.body()!!.toString())
                        } else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
                            Timber.tag("87878787887").d("secoonnddd")

//                            _getCurrentNewsResponse.postValue(Resource.error(it.message(), null))
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _getCurrentNewsResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _getCurrentNewsResponse.postValue(
                                Resource.error(
                                    "Some thing went wrong",
                                    null
                                )
                            )
                            Timber.tag("87878787887").d("third")

                        }
                    }
                } catch (e: Exception) {
                    _getCurrentNewsResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _getCurrentNewsResponse.postValue(Resource.error("No internet connection", null))
        }
    }


    private val _getUpcomingnewsResponse = MutableLiveData<Resource<NewsData>>()
    val getUpcomingnewsResponse: LiveData<Resource<NewsData>>
        get() = _getUpcomingnewsResponse

    fun getUpcomingNews(upcoming: Boolean) {
        viewModelScope.launch {
            _getUpcomingnewsResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    Timber.tag("87878787887").d("starta")

                    mainRepository.getUpcomingNews(upcoming).let {
                        if (it.isSuccessful) {
                            _getUpcomingnewsResponse.postValue(Resource.success(it.body()!!))
                            Timber.tag("87878787887").d(it.body()!!.toString())
                        } else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
                            Timber.tag("87878787887").d("secoonnddd")

//                            _getUpcomingnewsResponse.postValue(Resource.error(it.message(), null))
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _getUpcomingnewsResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _getUpcomingnewsResponse.postValue(
                                Resource.error(
                                    "Some thing went wrong",
                                    null
                                )
                            )
                            Timber.tag("87878787887").d("third")

                        }
                    }
                } catch (e: Exception) {
                    _getUpcomingnewsResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _getUpcomingnewsResponse.postValue(
                Resource.error(
                    "No internet connection",
                    null
                )
            )
        }
    }


    private val _getRecentNewsResponse = MutableLiveData<Resource<NewsData>>()
    val getRecentNewsResponse: LiveData<Resource<NewsData>>
        get() = _getRecentNewsResponse

    fun getRecentsNews(recents: Boolean) {
        viewModelScope.launch {
            _getRecentNewsResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    Timber.tag("87878787887").d("starta")

                    mainRepository.getRecentNews(recents).let {
                        if (it.isSuccessful) {
                            _getRecentNewsResponse.postValue(Resource.success(it.body()!!))
                            Timber.tag("87878787887").d(it.body()!!.toString())
                        } else if (it.code() == 500 || it.code() == 409 || it.code() == 502 || it.code() == 404 || it.code() == 400) {
                            Timber.tag("87878787887").d("secoonnddd")

//                            _getRecentNewsResponse.postValue(Resource.error(it.message(), null))
                            val jsonObj = JSONObject(it.errorBody()!!.charStream().readText())
                            _getRecentNewsResponse.postValue(Resource.error(jsonObj.getString("message")))
                        } else {
                            _getRecentNewsResponse.postValue(
                                Resource.error(
                                    "Some thing went wrong",
                                    null
                                )
                            )
                            Timber.tag("87878787887").d("third")

                        }
                    }
                } catch (e: Exception) {
                    _getRecentNewsResponse.postValue(Resource.error("${e.message}", null))
                }
            } else _getRecentNewsResponse.postValue(
                Resource.error(
                    "No internet connection",
                    null
                )
            )
        }
    }


}
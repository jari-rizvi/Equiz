package com.teamx.equiz.new_games



import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.teamx.equiz.baseclasses.BaseViewModel
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.data.remote.reporitory.MainRepository
import com.teamx.equiz.ui.fragments.quizresult.data.QuizResultDataModel
import com.teamx.equiz.ui.fragments.singlequize.model.SingleQuizData
import com.teamx.equiz.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class TapTheColorViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : BaseViewModel() {



}
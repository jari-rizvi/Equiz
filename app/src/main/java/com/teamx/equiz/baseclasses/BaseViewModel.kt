package com.teamx.equiz.baseclasses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.teamx.equiz.data.remote.Resource
import com.teamx.equiz.data.remote.reporitory.MainRepository
import com.teamx.equiz.ui.activity.mainActivity.activeusermodel.ModelActiveUser
import com.teamx.equiz.utils.NetworkHelper
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject


open class BaseViewModel: ViewModel() {

    private val _activeUser = MutableLiveData<String>()
    val activeUser: LiveData<String>
        get() = _activeUser

    fun setActiveUser(_activeUser: String) {
        this._activeUser.value = _activeUser
    }




}
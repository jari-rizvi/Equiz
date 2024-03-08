package com.teamx.equiz.baseclasses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


open class BaseViewModel : ViewModel() {

    private val _activeUser = MutableLiveData<String>()
    val activeUser: LiveData<String>
        get() = _activeUser

    fun setActiveUser(_activeUser: String) {
        this._activeUser.value = _activeUser
    }
}
package com.teamx.equiz.data.local.datastore


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.teamx.equiz.MainApplication.Companion.context
import com.teamx.equiz.constants.AppConstants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreProvider(context: Context) {

    //Create the dataStore
//    private val dataStore : DataStore<Preferences> = context.createDataStore(name = AppConstants.DataStore.DATA_STORE_NAME)

    //Create some keys
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = AppConstants.DataStore.DATA_STORE_NAME)
        val IS_LOCALIZATION_KEY = booleanPreferencesKey(AppConstants.DataStore.LOCALIZATION_KEY_NAME)
        val USER_NAME_KEY = stringPreferencesKey(AppConstants.DataStore.USER_NAME_KEY)
        val TOKEN = stringPreferencesKey(AppConstants.DataStore.TOKEN)

    }

    //Store data
    suspend fun storeData(isLocalizationKey: Boolean, name: String) {
        context.dataStore.edit {
            it[IS_LOCALIZATION_KEY] = isLocalizationKey
            it[USER_NAME_KEY] = name
        }
    }

    val token : Flow<String?> =  context.dataStore.data.map {
        it[TOKEN]
    }

    suspend fun saveUserToken(token: String){
        context.dataStore.edit {
            it[TOKEN] = token
        }
    }

    //Create an Localization flow
    val localizationFlow: Flow<Boolean> =  context.dataStore.data.map {
        it[IS_LOCALIZATION_KEY] ?: false
    }


    suspend fun removeAll() {
        context.dataStore.edit {
            it.remove(TOKEN)
//            it.remove(DETAILS)
//            it.remove(AVATAR)
//            it.remove(NAME)
//            it.remove(PAYTYPE)
//            it.remove((USER_ID))
//            it.remove((NUMBER))
        }

    }

}
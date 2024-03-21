package com.teamx.equiz.constants

import android.view.View
import androidx.annotation.StringDef
import com.google.android.material.snackbar.Snackbar


object AppConstants {

    @StringDef(ApiConfiguration.BASE_URL)
    annotation class ApiConfiguration {
        companion object {
//                        const val BASE_URL = "http://52.220.240.6:3003/"
//                        const val BASE_URL = "https://dev-api.emiratesq.net/"
            const val BASE_URL = "http://192.168.100.33:3003" /**local**/
//            const val BASE_URL = "http://192.168.100.49:3003" /**Sohail'slocal**/
//            const val BASE_URL = "http://192.168.100.79:3003" /**Tooba'slocal**/
//            const val BASE_URL = "https://qd6m9n13-3003.uks1.devtunnels.ms"
//            const val BASE_URL = "https://qd6m9n13-3003.uks1.devtunnels.ms"
//            const val BASE_URL = "https://qd6m9n13-3003.uks1.devtunnels.ms"

        }
    }


    @StringDef(DbConfiguration.DB_NAME)
    annotation class DbConfiguration {
        companion object {
            const val DB_NAME = "BaseProject"
        }
    }

    @StringDef(
        DataStore.DATA_STORE_NAME,
        DataStore.LOCALIZATION_KEY_NAME,
        DataStore.USER_NAME_KEY,
        DataStore.TOKEN
    )
    annotation class DataStore {
        companion object {
            const val DATA_STORE_NAME = "BaseProject"
            const val LOCALIZATION_KEY_NAME = "lang"
            const val USER_NAME_KEY = "user_name_key"
            const val CONTINUE_KEY = "continue_key"
            const val TOKEN = "token"
        }
    }

    fun showSnackBar(message: String, veiw:View) {
        Snackbar.make(
            veiw, message, Snackbar.LENGTH_SHORT
        ).show()
    }

}
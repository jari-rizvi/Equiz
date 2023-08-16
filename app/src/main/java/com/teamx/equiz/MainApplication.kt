package com.teamx.equiz

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MainApplication : Application() {


    companion object {
        var application: Application? = null
            private set
        val context: Context
            get() = application!!.applicationContext
        val PACKAGE_NAME: String
            get() = application!!.packageName
    }

    override fun onCreate() {
        super.onCreate()

        application = this

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

    }

}
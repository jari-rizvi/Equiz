package com.teamx.equiz

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import com.teamx.equiz.utils.CounterNotificationService
import com.teamx.equiz.utils.localization.LocaleManager
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MainApplication : Application() {


    companion object {
        var localeManager: LocaleManager? = null
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
        createNotificationChannel()
    }

    override fun attachBaseContext(base: Context?) {
        localeManager = LocaleManager(base!!)
        super.attachBaseContext(localeManager!!.setLocale(base!!))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig!!)
        localeManager!!.setLocale(this)
    }


    //notifications
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CounterNotificationService.COUNTER_CHANNEL_ID,
                "Counter",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "Used for the increment counter notifications"

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }



}
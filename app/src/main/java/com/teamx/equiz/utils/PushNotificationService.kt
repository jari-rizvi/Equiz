package com.teamx.equiz.utils

import android.content.Intent
import android.content.res.Configuration
import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.ktx.messaging
import com.google.gson.Gson
import com.teamx.equiz.NotificationHelper
import com.teamx.equiz.utils.notificationModel.NotificationModell
import org.json.JSONObject
import timber.log.Timber


class PushNotificationService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("123123", "onMessageReceiveddata:12")
        super.onMessageReceived(remoteMessage)

        if (remoteMessage.data.isNotEmpty()) {
           Timber.tag("123123").d("onMessageReceiveddata:12")
            // Handle data payload
            processNotificationData(remoteMessage.data);
        }
        if (remoteMessage.notification != null) {
            Log.d("123123", "onMessageReceiveddata:12")
            Log.d("123123", "onMessageReceived1: ${remoteMessage!!}")
            Log.d("123123", "onMessageReceived2: ${remoteMessage.data}")
            Log.d("123123", "onMessageReceived3: ${remoteMessage.data.get("img")}")
            Log.d("123123", "onMessageReceived4: ${remoteMessage.notification?.body}")
            val title = remoteMessage.notification!!.title
            val description = remoteMessage.notification!!.body
            val data = remoteMessage.data
            Log.d("123123", "onMessageReceived5:$description ")
            Log.d("123123", "onMessageReceived6:$title ")
            try {


                val jsonData = JSONObject(remoteMessage.data.get("data"))
                val data3 = Gson().fromJson(remoteMessage.data.get("data"), NotificationModell::class.java)
                Log.d("123123", "onMessageReceived7:${jsonData.get("type")} ")
                Log.d("123123", "onMessageReceived8:${jsonData.get("img")} ")
                Log.d("123123", "onMessageReceived9:${jsonData.get("orderID")} ")
                Log.d("123123", "onMessageReceiveddata:${data3}")
                Log.d("123123", "onMessageReceived0:${jsonData.get("actionText")} ")
                if (data3 == null) {
                    NotificationHelper.displayNotification(
                        applicationContext, title, description.toString() ?: ""
                    )
                } else {
                    NotificationHelper.displayNotification(
                        applicationContext, title, description.toString() ?: ""
                    )
                }
            } catch (e: Exception) {
                Log.d("123123", "onMessageReceiveddata:12")
                Log.d("123123", "onMessageReceived: ")
                e.printStackTrace()
                NotificationHelper.displayNotification(
                    applicationContext, title, description.toString() ?: ""
                )

            }

//            NotificationHelper.displayNotification(applicationContext, title, description ?: "")

        }
        Firebase.messaging.isAutoInitEnabled = true
        Log.d("123123", "onMessageReceived: ")

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
    private fun processNotificationData(data: Map<String, String>) {
        // Process data payload

        Log.d("123123", "Refreshed tokendata: $data")
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }
    override fun onNewToken(token: String) {
        Log.d("123123", "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
//        sendRegistrationToServer(token)
    }


}
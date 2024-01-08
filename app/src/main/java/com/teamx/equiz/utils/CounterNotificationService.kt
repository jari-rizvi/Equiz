package com.teamx.equiz.utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import com.teamx.equiz.PushNotificationService
import com.teamx.equiz.R
import com.teamx.equiz.ui.activity.mainActivity.MainActivity
import com.teamx.equiz.utils.notificationModel.NotificationModell


class CounterNotificationService(
    private val context: Context
) {
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


    fun showNotification1(header: String, comment: String) {
        val activityIntent = Intent(context, MainActivity::class.java)
        val bundle = Bundle()
        bundle.putString("str", "firstTest")
        activityIntent.putExtra("str", "firstTest")
        activityIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val activityPendingIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0,
            bundle
        )
        val incrementIntent = PendingIntent.getBroadcast(
            context,
            2,
            Intent(context, PushNotificationService::class.java),
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        )
        val notification = NotificationCompat.Builder(context, COUNTER_CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(header)
            .setContentText(comment)
            .setContentIntent(activityPendingIntent)
           /* .addAction(
                R.mipmap.ic_launcher,
                "View",
                activityPendingIntent
            )*/
            .build()

        Log.d("ShowNotification", "showNotification1: $header")
        Log.d("ShowNotification", "showNotification1: ")


        notificationManager.notify(1, notification)
    }





    companion object {
        const val COUNTER_CHANNEL_ID = "counter_channel"
        var notificationModell: NotificationModell? = null
    }


}

class CounterNotificationReceiver3 : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        Log.d("abcabc", "onReceive:123123@ ${intent?.extras?.size()}")

        val activityIntent = Intent(context, MainActivity::class.java)
        activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        ////////////

        CounterNotificationService.notificationModell?.let {


            activityIntent.putExtra("order_status", it.orderStatus)
            activityIntent.putExtra("order_id", it.orderID)

            activityIntent.putExtra(
                "orderId", it.orderID
            ).toString()
            activityIntent.putExtra(
                "ridingStatus", "pending"
            )

            activityIntent.putExtra(
                "lat", it.shopDetail.shopAddress.lat
            ).toString()
            activityIntent.putExtra(
                "lng", it.shopDetail.shopAddress.lng
            ).toString()

            activityIntent.putExtra(
                "shopImg", it.shopDetail.shopLogo
            ).toString()
            activityIntent.putExtra(
                "shopName", it.shopDetail.shopName
            ).toString()
            activityIntent.putExtra(
                "shopAdr", it.shopDetail.shopAddress.formattedAddress
            ).toString()

            PrefHelper.getInstance(context).saveOrderId(it.orderID)
            activityIntent.putExtra("order_status", it.orderStatus)
        }
//        activityIntent.putExtra("order_status", "Processing")

        //////////////
        context.startActivity(activityIntent)
//        service.showNotification(++Counter.value)
    }
}
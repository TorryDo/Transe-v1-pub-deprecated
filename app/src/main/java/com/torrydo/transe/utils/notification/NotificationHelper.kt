package com.torrydo.transe.utils.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NotificationHelper(
    private val context: Context
) {


    fun showNotification(noti : Noti){

        val notiManager = NotificationManagerCompat.from(context)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val notiChannel = NotificationChannel(noti.id, noti.name, NotificationManager.IMPORTANCE_DEFAULT)
            notiManager.createNotificationChannel(notiChannel)
        }


        val builder = NotificationCompat.Builder(context,noti.id)
            .setSmallIcon(noti.smallIcon)
            .setContentTitle(noti.contentTitle)
            .setContentText(noti.contentText)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setStyle(NotificationCompat.BigTextStyle().bigText(noti.contentText))
            .setAutoCancel(true)
        notiManager.notify(noti.notificationId,builder.build())
    }



}
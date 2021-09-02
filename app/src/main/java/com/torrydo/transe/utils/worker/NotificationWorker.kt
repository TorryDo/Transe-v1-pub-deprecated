package com.torrydo.transe.utils.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.torrydo.transe.R
import com.torrydo.transe.utils.Utils
import com.torrydo.transe.utils.notification.Noti
import com.torrydo.transe.utils.notification.NotificationHelper

class NotificationWorker(
    private val context: Context,
    workerParameters: WorkerParameters
) : Worker(context, workerParameters) {

    private val id = "vocabNoti"
    private val notiName = "notiName"
    private val notiTitle = "VocabPlaceHolder"
    private val notiContent = "vocab description"
    private val notiSmallIcon = R.mipmap.rounded_blue_diamond
    private val notificationId = 1

    override fun doWork(): Result {

        NotificationHelper(context).showNotification(
            Noti(
                id = id,
                name = notiName,
                contentTitle = notiTitle,
                contentText = notiContent,
                smallIcon = notiSmallIcon,
                notificationId = notificationId,
            )
        )

        return Result.success()
    }

}
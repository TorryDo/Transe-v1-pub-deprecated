package com.torrydo.transe.utils.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.torrydo.transe.R
import com.torrydo.transe.dataSource.database.local.MyRoomDatabase
import com.torrydo.transe.dataSource.database.local.VocabDao
import com.torrydo.transe.utils.notification.Noti
import com.torrydo.transe.utils.notification.NotificationHelper

class NotificationWorker(
    private val context: Context,
    workerParameters: WorkerParameters
) : Worker(context, workerParameters) {

    private val id = "vocabNoti"
    private val notiName = "notiName"
    private val notiSmallIcon = R.mipmap.rounded_blue_diamond
    private val notificationId = 1

    override fun doWork(): Result {

        var db: VocabDao? = MyRoomDatabase.getMyRoomDatabase(context).vocabDao()

        val randomVocab = db?.getRandomVocab()

        val title = randomVocab?.vocab ?: "null"
        val content = randomVocab?.let {
            it.contentEng[0].innerEngResultList[0].title
        } ?: "null"

        NotificationHelper(context).showNotification(
            Noti(
                id = id,
                name = notiName,
                contentTitle = title,
                contentText = content,
                smallIcon = notiSmallIcon,
                notificationId = notificationId,
            )
        )

        db = null;

        return Result.success()
    }

}
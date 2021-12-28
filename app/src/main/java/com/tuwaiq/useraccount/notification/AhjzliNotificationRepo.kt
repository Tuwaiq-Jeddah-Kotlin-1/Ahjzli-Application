package com.tuwaiq.useraccount.notification

import androidx.work.*
import com.tuwaiq.useraccount.MainActivity

class AhjzliNotificationRepo () {
    private val notification = "You have 1 hour for you'r reservation"
    fun myNotification(mainActivity: MainActivity){
        val myWorkRequest= OneTimeWorkRequestBuilder<AhjzliWorker>()
            .setInputData(workDataOf(
                "title" to "Ahjzli",
                "message" to notification)
            )
            .build()
        WorkManager.getInstance(mainActivity)
            .enqueue(myWorkRequest)
    }
}

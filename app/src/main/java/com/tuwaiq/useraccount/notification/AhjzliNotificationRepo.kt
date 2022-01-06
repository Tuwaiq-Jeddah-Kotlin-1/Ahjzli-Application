package com.tuwaiq.useraccount.notification

import androidx.work.*
import com.tuwaiq.useraccount.MainActivity

class AhjzliNotificationRepo () {
    fun myNotification(mainActivity: MainActivity,notification:String){
        val myWorkRequest= OneTimeWorkRequestBuilder<AhjzliWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setInputData(workDataOf(
                "title" to "Ahjzli",
                "message" to notification)
            )
            .build()
        WorkManager.getInstance(mainActivity)
            .enqueue(myWorkRequest)
    }
}

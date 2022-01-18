package com.tuwaiq.useraccount.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.tuwaiq.useraccount.ui.MainActivity
import com.tuwaiq.useraccount.R

class NotificationHelper(val context: Context) {
    private val channelId = "ahjzli_channel_id"
    private val notificationId = 1

    @RequiresApi(Build.VERSION_CODES.S)
    fun createNotification(title: String, message: String){
        createNotificationChannel()
        val intent= Intent(context, MainActivity::class.java).apply {
            flags= Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            this.putExtra("titleTest","messageTest")
        }

        val pendingIntent = PendingIntent.getActivity(context,0,intent, PendingIntent.FLAG_MUTABLE)
        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_notificatios)
            .setContentTitle(title)
            .setContentText(message)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()
        NotificationManagerCompat.from(context).notify(notificationId,notification)

    }

    private fun createNotificationChannel(){

            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId,
                channelId, importance).apply {
                description = "ahjzli Channel description"
            }
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

    }

package com.example.group3project.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.group3project.R

object Common {
    fun showNotification(context: Context, id: Int, title: String, body: String, intent: Intent?) {
        Log.i("Firebase-notification", "$title $body")
        var pendingIntent: PendingIntent? = null

        if (intent != null) {
            pendingIntent = PendingIntent.getActivity(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }
        val NOTIFICATION_CHANNEL_ID = "Project2"
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Greater than Oreo
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Project2",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationChannel.description = "Project2"
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.setVibrationPattern(longArrayOf(0, 1000, 500, 1000))
            notificationChannel.enableVibration(true)

            notificationManager.createNotificationChannel(notificationChannel)
        }

        val builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
        builder.setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.canada_computers)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.canada_computers))
        if (pendingIntent != null) {
            builder.setContentIntent(pendingIntent)
        }
        val notification: Notification = builder.build()
        notificationManager.notify(id, notification)
    }
}
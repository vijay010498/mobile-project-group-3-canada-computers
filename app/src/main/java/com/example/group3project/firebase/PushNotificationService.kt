package com.example.group3project.firebase

import com.example.group3project.utils.Common
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.util.Random

class PushNotificationService : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        if (message.notification != null) {
            message.notification!!.title?.let {
                message.notification!!.body?.let { it1 ->
                    Common.showNotification(
                        this,
                        Random().nextInt(),
                        it,
                        it1,
                        null
                    )
                }
            }
        }
    }
}
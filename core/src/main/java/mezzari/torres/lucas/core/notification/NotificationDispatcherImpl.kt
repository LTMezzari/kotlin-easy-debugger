package mezzari.torres.lucas.core.notification

import android.annotation.SuppressLint
import android.app.Notification
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationManagerCompat
import mezzari.torres.lucas.core.archive.createNotificationChannel

/**
 * @author Lucas T. Mezzari
 * @since 23/08/24
 */
class NotificationDispatcherImpl : NotificationDispatcher {

    override fun createChannel(
        context: Context,
        channelId: String,
        channelName: String,
        description: String?
    ): Boolean {
        createNotificationChannel(context, channelId, channelName, description)
        return true
    }

    @SuppressLint("MissingPermission")
    override fun sendNotification(
        context: Context, notification: Notification, id: Int, tag: String?
    ): Boolean {
        val manager = NotificationManagerCompat.from(context)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && !manager.areNotificationsEnabled()) {
            return false
        }

        tag?.run {
            manager.notify(this, id, notification)
            return true
        }
        manager.notify(id, notification)
        return true
    }

    override fun cancelNotification(
        context: Context, id: Int
    ): Boolean {
        val manager = NotificationManagerCompat.from(context)
        manager.cancel(id)
        return true
    }
}
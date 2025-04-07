package mezzari.torres.lucas.easy_debugger.notification

import android.app.Notification
import android.content.Context

/**
 * @author Lucas T. Mezzari
 * @since 23/08/24
 */
interface NotificationDispatcher {
    fun createChannel(
        context: Context,
        channelId: String,
        channelName: String,
        description: String? = null
    ): Boolean

    fun sendNotification(
        context: Context, notification: Notification, id: Int, tag: String? = null
    ): Boolean

    fun cancelNotification(
        context: Context, id: Int
    ): Boolean

    companion object {
        const val DEFAULT_CHANNEL = "default"
    }
}
package mezzari.torres.lucas.easy_debugger.archive

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationManagerCompat

/**
 * @author Lucas T. Mezzari
 * @since 23/08/24
 */
fun createNotificationChannel(
    context: Context,
    channelId: String,
    channelName: String,
    description: String? = null
) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
        return

    val mChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
    description?.run { mChannel.description = this }
    val manager = NotificationManagerCompat.from(context)
    manager.createNotificationChannel(mChannel)
}
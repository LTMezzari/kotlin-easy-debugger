package mezzari.torres.lucas.easy_debugger.debug.service

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import mezzari.torres.lucas.easy_debugger.debug.receiver.DebugNotificationReceiver
import mezzari.torres.lucas.easy_debugger.di.appLogger
import mezzari.torres.lucas.easy_debugger.di.notificationDispatcher
import mezzari.torres.lucas.easy_debugger.generic.BaseService
import mezzari.torres.lucas.easy_debugger.logger.AppLogger
import mezzari.torres.lucas.easy_debugger.notification.NotificationDispatcher

/**
 * @author Lucas T. Mezzari
 * @since 07/12/25
 **/
class DebugNotificationService: BaseService() {

    private val mAppLogger: AppLogger by lazy { appLogger }
    private val dispatcher: NotificationDispatcher by lazy { notificationDispatcher }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        showNotification(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        dispatcher.cancelNotification(this, NOTIFICATION_ID)
        mAppLogger.logMessage("Destroying Debug Notification")
    }

    private fun showNotification(context: Context) {
        mAppLogger.logMessage("Showing Debug Notification")
        dispatcher.createChannel(context, CHANNEL_ID, "Debug Channel")
        val actionIntent = Intent(context, DebugNotificationReceiver::class.java)
        val actionPendingIntent: PendingIntent =
            PendingIntent.getBroadcast(context, 0, actionIntent, PendingIntent.FLAG_IMMUTABLE)
        val notification =
            NotificationCompat.Builder(context, NotificationDispatcher.DEFAULT_CHANNEL)
                .setOngoing(true)
                .setSilent(true)
                .setSmallIcon(android.R.mipmap.sym_def_app_icon)
                .setContentTitle("Easy Debug Running")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setChannelId(CHANNEL_ID)
                .addAction(
                    android.R.drawable.ic_popup_sync, "Menu",
                    actionPendingIntent
                ).build()
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE -> startForeground(
                NOTIFICATION_ID,
                notification,
                ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE
            )
            else -> startForeground(NOTIFICATION_ID, notification)
        }
    }

    companion object {
        const val NOTIFICATION_ID = 1313142
        const val CHANNEL_ID = "DEBUG_NOTIFICATION_CHANNEL"
    }
}
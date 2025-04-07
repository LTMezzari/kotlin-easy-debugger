package mezzari.torres.lucas.easy_debugger.record.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import mezzari.torres.lucas.easy_debugger.notification.NotificationDispatcher
import mezzari.torres.lucas.easy_debugger.di.notificationDispatcher
import mezzari.torres.lucas.easy_debugger.di.screenRecordManager
import mezzari.torres.lucas.easy_debugger.record.manager.ScreenRecordManager
import mezzari.torres.lucas.easy_debugger.record.service.ScreenRecordService

/**
 * @author Lucas T. Mezzari
 * @since 31/03/25
 **/
internal class OnScreenRecordStoppedReceiver : BroadcastReceiver() {

    private val manager: ScreenRecordManager by lazy { screenRecordManager }
    private val dispatcher: NotificationDispatcher by lazy { notificationDispatcher }

    override fun onReceive(context: Context?, intent: Intent?) {
        manager.stopRecording()
        context?.run {
            dispatcher.cancelNotification(context, ScreenRecordService.NOTIFICATION_ID)
        }
    }
}
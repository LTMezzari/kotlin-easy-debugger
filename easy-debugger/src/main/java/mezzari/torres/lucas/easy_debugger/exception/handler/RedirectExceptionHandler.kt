package mezzari.torres.lucas.easy_debugger.exception.handler

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import kotlin.reflect.KClass
import kotlin.system.exitProcess


/**
 * @author Lucas T. Mezzari
 * @since 19/02/2020
 */
class RedirectExceptionHandler(
    private val activity: KClass<*>
) : ExceptionHandler {
    override fun uncaughtException(context: Activity, thread: Thread, throwable: Throwable) {
        val intent: PendingIntent
        if (Build.VERSION.SDK_INT >= 31) {
            intent = PendingIntent.getActivity(
                context.application,
                0,
                Intent(context, activity.java).putExtra("exception", throwable),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        } else {
            intent = PendingIntent.getActivity(
                context.application,
                0,
                Intent(context, activity.java).putExtra("exception", throwable),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }

        val mgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + RESTART_TIME, intent)
        exitProcess(2)
    }

    companion object {
        const val RESTART_TIME = 10
    }
}
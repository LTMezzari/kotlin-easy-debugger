package mezzari.torres.lucas.easy_debugger.exception.handler

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import kotlin.reflect.KClass
import kotlin.system.exitProcess


/**
 * @author Lucas T. Mezzari
 * @since 19/02/2020
 */
class RedirectExceptionHandler(
    private val activity: KClass<*>
): ExceptionHandler {
    override fun uncaughtException(context: Activity, thread: Thread, throwable: Throwable) {
        val intent = PendingIntent.getActivity(
            context.application,
            0,
            Intent(context, activity.java).putExtra("exception", throwable),
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val mgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + RESTART_TIME, intent)
        exitProcess(2)
    }

    companion object {
        const val RESTART_TIME = 10
    }
}
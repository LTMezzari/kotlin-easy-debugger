package mezzari.torres.lucas.easy_debugger.exception.handler

import android.app.Activity

/**
 * @author Lucas T. Mezzari
 * @since 19/02/2020
 */
interface ExceptionHandler {
    fun uncaughtException(context: Activity, thread: Thread, throwable: Throwable)
}
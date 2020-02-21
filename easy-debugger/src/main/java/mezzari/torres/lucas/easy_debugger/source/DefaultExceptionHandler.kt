package mezzari.torres.lucas.easy_debugger.source

import mezzari.torres.lucas.easy_debugger.generic.ExceptionHandler

/**
 * @author Lucas T. Mezzari
 * @since 19/02/2020
 */
internal class DefaultExceptionHandler(
    private val defaultExceptionHandler: Thread.UncaughtExceptionHandler,
    private val exceptionHandler: ExceptionHandler,
    private val shouldUseDefault: Boolean
): Thread.UncaughtExceptionHandler {

    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        val activity = EasyDebugger.lastStartedActivity
        if (shouldUseDefault || activity == null) {
            defaultExceptionHandler.uncaughtException(thread, throwable)
            return
        }

        exceptionHandler.uncaughtException(activity, thread, throwable)
    }
}
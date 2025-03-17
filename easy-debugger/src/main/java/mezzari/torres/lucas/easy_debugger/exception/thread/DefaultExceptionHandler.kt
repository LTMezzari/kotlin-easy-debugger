package mezzari.torres.lucas.easy_debugger.exception.thread

import mezzari.torres.lucas.easy_debugger.EasyDebugger
import mezzari.torres.lucas.easy_debugger.exception.handler.ExceptionHandler
import mezzari.torres.lucas.easy_debugger.navigation.ActivityNavigationModule

/**
 * @author Lucas T. Mezzari
 * @since 19/02/2020
 */
internal class DefaultExceptionHandler(
    private val defaultExceptionHandler: Thread.UncaughtExceptionHandler,
    private val exceptionHandler: ExceptionHandler,
    private val shouldUseDefault: Boolean
) : Thread.UncaughtExceptionHandler {

    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        val debugger = EasyDebugger.instance
        val navigationModule =
            debugger.getModuleByType<ActivityNavigationModule>() ?: return
        val activity =
            navigationModule.listener.currentActivity?.activity?.get()
        if (shouldUseDefault || activity == null) {
            defaultExceptionHandler.uncaughtException(thread, throwable)
            return
        }

        exceptionHandler.uncaughtException(activity, thread, throwable)
    }
}
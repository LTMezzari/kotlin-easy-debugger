package mezzari.torres.lucas.easy_debugger.exception

import android.app.Application
import mezzari.torres.lucas.easy_debugger.EasyDebugger
import mezzari.torres.lucas.easy_debugger.exception.handler.ExceptionHandler
import mezzari.torres.lucas.easy_debugger.exception.thread.DefaultExceptionHandler
import mezzari.torres.lucas.easy_debugger.interfaces.DebuggerModule

/**
 * @author Lucas T. Mezzari
 * @since 16/03/25
 **/
internal class ExceptionModule : DebuggerModule {
    override fun onDebuggerInitialization(application: Application, debugger: EasyDebugger) {
        setupExceptionHandler(debugger.exceptionHandler, debugger.shouldUseDefaultHandler)
    }

    private fun setupExceptionHandler(
        exceptionHandler: ExceptionHandler,
        shouldUseDefaultHandler: Boolean
    ) {
        val defaultHandler: Thread.UncaughtExceptionHandler =
            Thread.getDefaultUncaughtExceptionHandler()
                ?: Thread.UncaughtExceptionHandler { _, t -> t.printStackTrace() }

        Thread.setDefaultUncaughtExceptionHandler(
            DefaultExceptionHandler(
                defaultHandler,
                exceptionHandler,
                shouldUseDefaultHandler
            )
        )
    }
}
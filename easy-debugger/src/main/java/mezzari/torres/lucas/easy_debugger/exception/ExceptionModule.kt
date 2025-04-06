package mezzari.torres.lucas.easy_debugger.exception

import android.app.Application
import mezzari.torres.lucas.easy_debugger.EasyDebugger
import mezzari.torres.lucas.easy_debugger.di.exceptionHandler
import mezzari.torres.lucas.easy_debugger.exception.handler.ExceptionHandler
import mezzari.torres.lucas.easy_debugger.exception.thread.DefaultExceptionHandler
import mezzari.torres.lucas.easy_debugger.file.FileProviderConfiguration
import mezzari.torres.lucas.easy_debugger.interfaces.DebuggerModule

/**
 * @author Lucas T. Mezzari
 * @since 16/03/25
 **/
internal class ExceptionModule(
    private val handler: ExceptionHandler,
    private val shouldUseDefaultHandler: Boolean,
    internal val fileConfiguration: FileProviderConfiguration
) : DebuggerModule {
    override fun onDebuggerInitialization(application: Application, debugger: EasyDebugger) {
        setupExceptionHandler(handler, shouldUseDefaultHandler)
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

fun EasyDebugger.setExceptionModule(
    handler: ExceptionHandler = exceptionHandler,
    shouldUseDefaultHandler: Boolean = false,
) {
    val configuration = configuration.fileProviderConfiguration
        ?: throw Exception("A File Provider Configuration should be setup for Exception Module to work")
    addModule(
        ExceptionModule(
            handler,
            shouldUseDefaultHandler,
            configuration
        )
    )
}
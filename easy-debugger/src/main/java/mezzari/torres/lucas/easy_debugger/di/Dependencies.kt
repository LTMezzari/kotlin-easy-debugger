package mezzari.torres.lucas.easy_debugger.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import mezzari.torres.lucas.core.interfaces.AppDispatcher
import mezzari.torres.lucas.core.logger.AppLogger
import mezzari.torres.lucas.core.logger.AppLoggerImpl
import mezzari.torres.lucas.core.service.FloatingWindowManager
import mezzari.torres.lucas.core.service.FloatingWindowManagerImpl
import mezzari.torres.lucas.easy_debugger.EasyDebugger
import mezzari.torres.lucas.easy_debugger.exception.handler.ExceptionHandler
import mezzari.torres.lucas.easy_debugger.exception.handler.RedirectExceptionHandler
import mezzari.torres.lucas.easy_debugger.exception.view.ExceptionActivity
import mezzari.torres.lucas.easy_debugger.logs.listener.LogListener
import mezzari.torres.lucas.easy_debugger.logs.listener.LogListenerImpl

/**
 * @author Lucas T. Mezzari
 * @since 25/03/25
 **/
val appDispatcher: AppDispatcher = object : AppDispatcher {
    override val main: CoroutineDispatcher
        get() = Dispatchers.Main
    override val io: CoroutineDispatcher
        get() = Dispatchers.IO
}

val appLogger: AppLogger = AppLoggerImpl(EasyDebugger.instance.configuration)

val logListener: LogListener = LogListenerImpl(appDispatcher)

val exceptionHandler: ExceptionHandler = RedirectExceptionHandler(ExceptionActivity::class)

val floatingWindowManager: FloatingWindowManager = FloatingWindowManagerImpl(appLogger)
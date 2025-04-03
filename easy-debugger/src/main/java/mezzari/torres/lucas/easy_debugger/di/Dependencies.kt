package mezzari.torres.lucas.easy_debugger.di

import mezzari.torres.lucas.core.di.appDispatcher
import mezzari.torres.lucas.core.logger.AppLogger
import mezzari.torres.lucas.core.logger.AppLoggerImpl
import mezzari.torres.lucas.core.record.ScreenRecordManager
import mezzari.torres.lucas.core.record.ScreenRecordManagerImpl
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
internal val appLogger: AppLogger = AppLoggerImpl(EasyDebugger.instance.configuration)

internal val logListener: LogListener = LogListenerImpl(appDispatcher)

internal val exceptionHandler: ExceptionHandler = RedirectExceptionHandler(ExceptionActivity::class)

internal val floatingWindowManager: FloatingWindowManager = FloatingWindowManagerImpl(appLogger)

internal val screenRecordManager: ScreenRecordManager = ScreenRecordManagerImpl(appLogger)
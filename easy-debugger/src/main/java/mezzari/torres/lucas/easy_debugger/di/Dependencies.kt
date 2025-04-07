package mezzari.torres.lucas.easy_debugger.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import mezzari.torres.lucas.easy_debugger.file.manager.FileManager
import mezzari.torres.lucas.easy_debugger.file.manager.FileManagerImpl
import mezzari.torres.lucas.easy_debugger.interfaces.AppDispatcher
import mezzari.torres.lucas.easy_debugger.logger.AppLogger
import mezzari.torres.lucas.easy_debugger.logger.AppLoggerImpl
import mezzari.torres.lucas.easy_debugger.notification.NotificationDispatcher
import mezzari.torres.lucas.easy_debugger.notification.NotificationDispatcherImpl
import mezzari.torres.lucas.easy_debugger.print.PrintScreenManager
import mezzari.torres.lucas.easy_debugger.print.PrintScreenManagerImpl
import mezzari.torres.lucas.easy_debugger.record.manager.ScreenRecordManager
import mezzari.torres.lucas.easy_debugger.record.manager.ScreenRecordManagerImpl
import mezzari.torres.lucas.easy_debugger.service.FloatingWindowManager
import mezzari.torres.lucas.easy_debugger.service.FloatingWindowManagerImpl
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

val fileManager: FileManager = FileManagerImpl()

val printScreenManager: PrintScreenManager = PrintScreenManagerImpl()

val notificationDispatcher: NotificationDispatcher = NotificationDispatcherImpl()

internal val appLogger: AppLogger = AppLoggerImpl(EasyDebugger.instance.configuration)

internal val logListener: LogListener = LogListenerImpl(appDispatcher)

internal val exceptionHandler: ExceptionHandler = RedirectExceptionHandler(ExceptionActivity::class)

internal val floatingWindowManager: FloatingWindowManager = FloatingWindowManagerImpl(appLogger)

internal val screenRecordManager: ScreenRecordManager = ScreenRecordManagerImpl(appLogger)
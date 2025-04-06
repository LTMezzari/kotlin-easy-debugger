package mezzari.torres.lucas.core.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import mezzari.torres.lucas.core.file.FileManager
import mezzari.torres.lucas.core.file.FileManagerImpl
import mezzari.torres.lucas.core.interfaces.AppDispatcher
import mezzari.torres.lucas.core.notification.NotificationDispatcher
import mezzari.torres.lucas.core.notification.NotificationDispatcherImpl
import mezzari.torres.lucas.core.print.PrintScreenManager
import mezzari.torres.lucas.core.print.PrintScreenManagerImpl

/**
 * @author Lucas T. Mezzari
 * @since 29/03/25
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
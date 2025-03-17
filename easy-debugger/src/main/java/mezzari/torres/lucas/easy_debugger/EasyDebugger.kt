package mezzari.torres.lucas.easy_debugger

import android.app.Application
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import mezzari.torres.lucas.easy_debugger.debug.FloatingDebugViewModule
import mezzari.torres.lucas.easy_debugger.interfaces.DebuggerModule
import mezzari.torres.lucas.easy_debugger.debug.dialog.model.DebugOption
import mezzari.torres.lucas.easy_debugger.exception.ExceptionModule
import mezzari.torres.lucas.easy_debugger.exception.handler.ExceptionHandler
import mezzari.torres.lucas.easy_debugger.exception.handler.RedirectExceptionHandler
import mezzari.torres.lucas.easy_debugger.exception.view.ExceptionActivity
import mezzari.torres.lucas.easy_debugger.interfaces.AppDispatcher
import mezzari.torres.lucas.easy_debugger.logs.LogModule
import mezzari.torres.lucas.easy_debugger.logs.listener.LogListener
import mezzari.torres.lucas.easy_debugger.logs.listener.LogListenerImpl
import mezzari.torres.lucas.easy_debugger.navigation.ActivityNavigationModule
import mezzari.torres.lucas.easy_debugger.settings.SettingsModule
import kotlin.reflect.KClass

/**
 * @author Lucas T. Mezzari
 * @since 19/02/2020
 */
class EasyDebugger private constructor() {

    private val appDispatcher: AppDispatcher = object : AppDispatcher {
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
    }

    private lateinit var application: Application

    private val modules: ArrayList<DebuggerModule> = arrayListOf()
    val debugOptions: ArrayList<DebugOption> = arrayListOf()
    val navigationListeners: ArrayList<Application.ActivityLifecycleCallbacks> = arrayListOf()
    val activityListener: Application.ActivityLifecycleCallbacks? get() = null

    val exceptionHandler: ExceptionHandler = RedirectExceptionHandler(ExceptionActivity::class)
    val shouldUseDefaultHandler: Boolean = false

    val logListener: LogListener = LogListenerImpl(appDispatcher)

    private val allModules: List<DebuggerModule> get() = defaultModules + modules

    private val defaultModules: List<DebuggerModule> = listOf(
        ExceptionModule(),
        LogModule(),
        ActivityNavigationModule(),
        FloatingDebugViewModule(),
        SettingsModule(),
    )

    fun setApplication(application: Application) {
        this.application = application
    }

    fun addModule(module: DebuggerModule) {
        this.modules.add(module)
    }

    fun <T : DebuggerModule> getModuleByType(type: KClass<*>): T? {
        return allModules.firstOrNull { it::class == type } as? T
    }

    inline fun <reified T : DebuggerModule> getModuleByType(): T? {
        return getModuleByType(T::class)
    }

    private fun initialize() {
        if (!::application.isInitialized) {
            throw RuntimeException("Application must be set")
        }
        allModules.forEach {
            it.onDebuggerInitialization(application, this)
            it.onCreateDebugOptions(debugOptions)
        }
    }

    companion object {
        lateinit var instance: EasyDebugger

        fun setupDebugger(block: EasyDebugger.() -> Unit) {
            if (::instance.isInitialized) {
                throw RuntimeException("Easy Debugger already initialized")
            }
            instance = EasyDebugger()
            block(instance)
            instance.initialize()
        }
    }
}
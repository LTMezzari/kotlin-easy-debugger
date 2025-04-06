package mezzari.torres.lucas.easy_debugger

import android.app.Application
import mezzari.torres.lucas.easy_debugger.source.Configuration
import mezzari.torres.lucas.easy_debugger.interfaces.DebuggerModule
import mezzari.torres.lucas.easy_debugger.debug.model.DebugOption
import mezzari.torres.lucas.easy_debugger.file.FileProviderConfiguration
import kotlin.reflect.KClass

/**
 * @author Lucas T. Mezzari
 * @since 19/02/2020
 */
class EasyDebugger private constructor() {

    private lateinit var application: Application

    private val modules: ArrayList<DebuggerModule> = arrayListOf()
    val debugOptions: ArrayList<DebugOption> = arrayListOf()
    val navigationListeners: ArrayList<Application.ActivityLifecycleCallbacks> = arrayListOf()
    var configuration: Configuration = Configuration()

    fun setLogsEnabled(isEnabled: Boolean) {
        configuration.isLogsEnabled = isEnabled
    }

    fun setFileProviderConfiguration(fileProviderConfiguration: FileProviderConfiguration) {
        configuration.fileProviderConfiguration = fileProviderConfiguration
    }

    fun setApplication(application: Application) {
        this.application = application
    }

    fun addModule(module: DebuggerModule) {
        this.modules.add(module)
    }

    fun addModules(modules: List<DebuggerModule>) {
        this.modules.addAll(modules)
    }

    fun <T : DebuggerModule> getModuleByType(type: KClass<*>): T? {
        return modules.firstOrNull { it::class == type } as? T
    }

    inline fun <reified T : DebuggerModule> getModuleByType(): T? {
        return getModuleByType(T::class)
    }

    private fun initialize() {
        if (!::application.isInitialized) {
            throw RuntimeException("Application must be set")
        }
        modules.forEach {
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
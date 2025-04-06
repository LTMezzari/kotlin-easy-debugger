package mezzari.torres.lucas.easy_debugger.record

import android.app.Activity
import android.app.Application
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import mezzari.torres.lucas.easy_debugger.record.manager.ScreenRecordManager
import mezzari.torres.lucas.easy_debugger.EasyDebugger
import mezzari.torres.lucas.easy_debugger.debug.model.DebugOption
import mezzari.torres.lucas.easy_debugger.di.screenRecordManager
import mezzari.torres.lucas.easy_debugger.file.FileProviderConfiguration
import mezzari.torres.lucas.easy_debugger.interfaces.DebuggerModule

/**
 * @author Lucas T. Mezzari
 * @since 31/03/25
 **/
internal class ScreenRecordModule(
    private val name: String,
    private val screenRecordManager: ScreenRecordManager,
    internal val fileProviderConfiguration: FileProviderConfiguration,
) : DebuggerModule, Application.ActivityLifecycleCallbacks {

    override fun onDebuggerInitialization(application: Application, debugger: EasyDebugger) {
        debugger.navigationListeners.add(this)
    }

    override fun onCreateDebugOptions(options: ArrayList<DebugOption>) {
        super.onCreateDebugOptions(options)
        options.add(DebugOption(name) {
            val activity = it as? FragmentActivity ?: return@DebugOption
            screenRecordManager.startRecording(activity)
        })
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        (activity as? FragmentActivity)?.lifecycle?.addObserver(screenRecordManager)
    }

    override fun onActivityStarted(activity: Activity) {}

    override fun onActivityResumed(activity: Activity) {}

    override fun onActivityPaused(activity: Activity) {}

    override fun onActivityStopped(activity: Activity) {
        (activity as? FragmentActivity)?.lifecycle?.removeObserver(screenRecordManager)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

    override fun onActivityDestroyed(activity: Activity) {}
}

fun EasyDebugger.setScreenRecordModule(
    name: String = "Screen Record",
    manager: ScreenRecordManager = screenRecordManager
) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
        return
    }
    val configuration = configuration.fileProviderConfiguration
        ?: throw Exception("A File Provider Configuration should be setup for Record Module to work")
    addModule(ScreenRecordModule(name, manager, configuration))
}
package mezzari.torres.lucas.easy_debugger.debug

import android.app.Activity
import android.app.Application
import android.os.Bundle
import mezzari.torres.lucas.easy_debugger.EasyDebugger
import mezzari.torres.lucas.easy_debugger.debug.service.FloatingDebugViewService
import mezzari.torres.lucas.easy_debugger.interfaces.DebuggerModule
import java.lang.Exception
import mezzari.torres.lucas.core.logger.AppLogger
import mezzari.torres.lucas.core.service.FloatingWindowManager
import mezzari.torres.lucas.easy_debugger.di.appLogger
import mezzari.torres.lucas.easy_debugger.di.floatingWindowManager
import mezzari.torres.lucas.easy_debugger.logs.LogModule

/**
 * @author Lucas T. Mezzari
 * @since 16/03/25
 **/
internal class DebugModule(
    private val manager: FloatingWindowManager,
    private val logger: AppLogger
) :
    DebuggerModule, Application.ActivityLifecycleCallbacks {

    internal var currentActiveModule: DebuggerModule? = null

    internal var currentActivity: Activity? = null
    private var startCount = 0

    private var hasPutTheFloatingView: Boolean = false

    override fun onDebuggerInitialization(application: Application, debugger: EasyDebugger) {
        debugger.navigationListeners.add(this)
        currentActiveModule = EasyDebugger.instance.getModuleByType<LogModule>()
    }

    override fun onActivityPaused(activity: Activity) {}

    override fun onActivityStarted(activity: Activity) {
        if (currentActivity != activity) {
            currentActivity = activity
        }
        startCount++
        if (!hasPutTheFloatingView) {
            placeDebugView(activity)
        }
    }

    override fun onActivityDestroyed(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {}

    override fun onActivityStopped(activity: Activity) {
        if (currentActivity == activity) {
            currentActivity = null
        }
        startCount--
        if (startCount <= 0) {
            if (hasPutTheFloatingView) {
                hasPutTheFloatingView = false
                stopService(activity)
            }
        }
    }

    override fun onActivityCreated(activity: Activity, bundle: Bundle?) {}

    override fun onActivityResumed(activity: Activity) {}

    private fun placeDebugView(activity: Activity) {
        try {
            if (hasPutTheFloatingView)
                return

            hasPutTheFloatingView = true
            startService(activity)
        } catch (e: Exception) {
            hasPutTheFloatingView = false
            logger.logError(e)
        }
    }

    private fun stopService(activity: Activity) {
        manager.destroyFloatingWindow(activity, FloatingDebugViewService::class)
    }

    private fun startService(activity: Activity) {
        manager.createFloatingWindow(activity, FloatingDebugViewService::class)
    }
}

fun EasyDebugger.setDebugModule() {
    addModule(DebugModule(floatingWindowManager, appLogger))
}
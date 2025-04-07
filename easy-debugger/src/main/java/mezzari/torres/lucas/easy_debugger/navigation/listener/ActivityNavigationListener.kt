package mezzari.torres.lucas.easy_debugger.navigation.listener

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import mezzari.torres.lucas.easy_debugger.logger.AppLogger
import mezzari.torres.lucas.easy_debugger.navigation.model.ActivityNavigationStack
import mezzari.torres.lucas.easy_debugger.navigation.model.ActivityWrapper

/**
 * @author Lucas T. Mezzari
 * @since 16/03/25
 **/
internal class ActivityNavigationListener(
    private val listener: Application.ActivityLifecycleCallbacks? = null,
    private val internalListeners: List<Application.ActivityLifecycleCallbacks> = listOf(),
    private val appLogger: AppLogger,
) : Application.ActivityLifecycleCallbacks {

    val stack = ActivityNavigationStack()

    val currentActivity: ActivityWrapper? get() = stack.getCurrentResumedActivity()

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        stack.addActivity(activity)
        internalListeners.forEach { it.onActivityCreated(activity, savedInstanceState) }
        listener?.onActivityCreated(activity, savedInstanceState)
        appLogger.logMessage("$activity Created")
    }

    override fun onActivityStarted(activity: Activity) {
        stack.updateActivityState(activity, Lifecycle.State.STARTED)
        internalListeners.forEach { it.onActivityStarted(activity) }
        listener?.onActivityStarted(activity)
        appLogger.logMessage("$activity Started")
    }

    override fun onActivityResumed(activity: Activity) {
        stack.updateActivityState(activity, Lifecycle.State.RESUMED)
        internalListeners.forEach { it.onActivityResumed(activity) }
        listener?.onActivityResumed(activity)
        appLogger.logMessage("$activity Resumed")
    }

    override fun onActivityPaused(activity: Activity) {
        internalListeners.forEach { it.onActivityPaused(activity) }
        listener?.onActivityPaused(activity)
        appLogger.logMessage("$activity Paused")
    }

    override fun onActivityDestroyed(activity: Activity) {
        stack.removeActivity(activity)
        internalListeners.forEach { it.onActivityDestroyed(activity) }
        listener?.onActivityDestroyed(activity)
        appLogger.logMessage("$activity Destroyed")
    }

    override fun onActivityStopped(activity: Activity) {
        internalListeners.forEach { it.onActivityStopped(activity) }
        listener?.onActivityStopped(activity)
        appLogger.logMessage("$activity Stopped")
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        internalListeners.forEach { it.onActivitySaveInstanceState(activity, outState) }
        listener?.onActivitySaveInstanceState(activity, outState)
        appLogger.logMessage("$activity Saved Instance State")
    }
}
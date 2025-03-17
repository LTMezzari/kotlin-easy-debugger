package mezzari.torres.lucas.easy_debugger.navigation.listener

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import mezzari.torres.lucas.easy_debugger.navigation.model.ActivityNavigationStack
import mezzari.torres.lucas.easy_debugger.navigation.model.ActivityWrapper

/**
 * @author Lucas T. Mezzari
 * @since 16/03/25
 **/
internal class ActivityNavigationListener(
    private val listener: Application.ActivityLifecycleCallbacks? = null,
    private val internalListeners: List<Application.ActivityLifecycleCallbacks> = listOf(),
) : Application.ActivityLifecycleCallbacks {

    private val stack = ActivityNavigationStack()

    val currentActivity: ActivityWrapper? get() = stack.getCurrentResumedActivity()

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        stack.addActivity(activity)
        internalListeners.forEach { it.onActivityCreated(activity, savedInstanceState) }
        listener?.onActivityCreated(activity, savedInstanceState)
    }

    override fun onActivityStarted(activity: Activity) {
        stack.updateActivityState(activity, Lifecycle.State.STARTED)
        internalListeners.forEach { it.onActivityStarted(activity) }
        listener?.onActivityStarted(activity)
    }

    override fun onActivityResumed(activity: Activity) {
        stack.updateActivityState(activity, Lifecycle.State.RESUMED)
        internalListeners.forEach { it.onActivityResumed(activity) }
        listener?.onActivityResumed(activity)
    }

    override fun onActivityPaused(activity: Activity) {
        internalListeners.forEach { it.onActivityPaused(activity) }
        listener?.onActivityPaused(activity)
    }

    override fun onActivityDestroyed(activity: Activity) {
        stack.removeActivity(activity)
        internalListeners.forEach { it.onActivityDestroyed(activity) }
        listener?.onActivityDestroyed(activity)
    }

    override fun onActivityStopped(activity: Activity) {
        internalListeners.forEach { it.onActivityStopped(activity) }
        listener?.onActivityStopped(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        internalListeners.forEach { it.onActivitySaveInstanceState(activity, outState) }
        listener?.onActivitySaveInstanceState(activity, outState)
    }
}
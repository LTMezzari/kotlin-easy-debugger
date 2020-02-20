package mezzari.torres.lucas.easy_debugger.source

import android.app.Activity
import android.app.Application
import android.os.Bundle

/**
 * @author Lucas T. Mezzari
 * @since 19/02/2020
 */
object EasyDebugger {

    internal var lastStartedActivity: Activity? = null
    internal var startCount = 0

    fun initialize(
        application: Application,
        configuration: EasyDebuggerConfiguration = EasyDebuggerConfiguration()
    ) {
        setupActivityListener(
            application,
            configuration
        )

        setupExceptionHandler(
            configuration
        )
    }

    private fun setupActivityListener(application: Application, configuration: EasyDebuggerConfiguration) {
        application.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityPaused(activity: Activity) {
                configuration.activityListener?.onActivityPaused(activity)
            }

            override fun onActivityStarted(activity: Activity) {
                startCount++
                lastStartedActivity = activity
                configuration.activityListener?.onActivityStarted(activity)
            }

            override fun onActivityDestroyed(activity: Activity) {
                configuration.activityListener?.onActivityDestroyed(activity)
            }

            override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {
                configuration.activityListener?.onActivitySaveInstanceState(activity, bundle)
            }

            override fun onActivityStopped(activity: Activity) {
                startCount--
                if (startCount <= 0) {
                    lastStartedActivity = null
                }
                configuration.activityListener?.onActivityStopped(activity)
            }

            override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
                configuration.activityListener?.onActivityCreated(activity, bundle)
            }

            override fun onActivityResumed(activity: Activity) {
                configuration.activityListener?.onActivityResumed(activity)
            }
        })
    }

    private fun setupExceptionHandler(configuration: EasyDebuggerConfiguration) {
        val defaultHandler: Thread.UncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
            ?: Thread.UncaughtExceptionHandler { _, t -> t.printStackTrace() }

        Thread.setDefaultUncaughtExceptionHandler(
            EasyDebuggerExceptionHandler(
                defaultHandler,
                configuration.exceptionHandler,
                configuration.shouldUseDefaultHandler
            )
        )
    }
}
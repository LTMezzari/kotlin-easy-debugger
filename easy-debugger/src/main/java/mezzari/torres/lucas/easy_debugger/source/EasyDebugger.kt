package mezzari.torres.lucas.easy_debugger.source

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import mezzari.torres.lucas.easy_debugger.service.FloatingDebugViewService

/**
 * @author Lucas T. Mezzari
 * @since 19/02/2020
 */
object EasyDebugger {

    internal var lastStartedActivity: Activity? = null
    internal var startCount = 0
    internal lateinit var configuration: EasyDebuggerConfiguration

    private var hasPutTheFloatingView: Boolean = false

    fun initialize(
        application: Application,
        configuration: EasyDebuggerConfiguration = EasyDebuggerConfiguration()
    ) {
        this.configuration = configuration
        setupActivityListener(application, configuration)
        setupExceptionHandler(configuration)
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

                if (startCount == 1 && configuration.hasFloatingView) {
                    placeDebugView(activity)
                }
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
                    hasPutTheFloatingView = false
                    activity.stopService(Intent(activity, FloatingDebugViewService::class.java))
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

    private fun placeDebugView(activity: Activity) {
        if (configuration.shouldUseDefaultHandler || hasPutTheFloatingView)
            return

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(activity)) {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,Uri.parse("package:" + activity.packageName))
            activity.startActivityForResult(intent, 2048)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(activity)) {
            hasPutTheFloatingView = true
            activity.startService(Intent(activity, FloatingDebugViewService::class.java))
        }
    }
}
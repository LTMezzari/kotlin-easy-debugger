package mezzari.torres.lucas.easy_debugger.source

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings

/**
 * @author Lucas T. Mezzari
 * @since 19/02/2020
 */
object EasyDebugger {

    private const val PERMISSION_OVERLAY = 2048

    private lateinit var application: Application
    internal lateinit var configuration: Configuration

    internal var lastStartedActivity: Activity? = null
    internal var startCount = 0

    private var hasPutTheFloatingView: Boolean = false

    fun builder(application: Application): Configuration.Builder {
        this.application = application
        return Configuration.Builder()
    }

    internal fun initialize(
        configuration: Configuration
    ) {
        this.configuration = configuration
        setupActivityListener(application, configuration)
        setupExceptionHandler(configuration)
    }

    private fun setupActivityListener(application: Application, configuration: Configuration) {
        application.registerActivityLifecycleCallbacks(object :
            Application.ActivityLifecycleCallbacks {
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

                    if (hasPutTheFloatingView) {
                        hasPutTheFloatingView = false
                        activity.stopService(
                            Intent(
                                activity,
                                configuration.floatingViewService.java
                            )
                        )
                    }
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

    private fun setupExceptionHandler(configuration: Configuration) {
        val defaultHandler: Thread.UncaughtExceptionHandler =
            Thread.getDefaultUncaughtExceptionHandler()
                ?: Thread.UncaughtExceptionHandler { _, t -> t.printStackTrace() }

        Thread.setDefaultUncaughtExceptionHandler(
            DefaultExceptionHandler(
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
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + activity.packageName)
            )
            activity.startActivityForResult(intent, PERMISSION_OVERLAY)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(
                activity
            )
        ) {
            hasPutTheFloatingView = true
            activity.startService(Intent(activity, configuration.floatingViewService.java))
        }
    }
}
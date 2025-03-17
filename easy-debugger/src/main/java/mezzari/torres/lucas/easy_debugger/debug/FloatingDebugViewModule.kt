package mezzari.torres.lucas.easy_debugger.debug

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import mezzari.torres.lucas.easy_debugger.EasyDebugger
import mezzari.torres.lucas.easy_debugger.debug.service.FloatingDebugViewService
import mezzari.torres.lucas.easy_debugger.interfaces.DebuggerModule
import java.lang.Exception
import java.lang.ref.WeakReference
import androidx.core.net.toUri

/**
 * @author Lucas T. Mezzari
 * @since 16/03/25
 **/
internal class FloatingDebugViewModule : DebuggerModule, Application.ActivityLifecycleCallbacks {

    internal var currentActivity: Activity? = null
    private var currentService: WeakReference<Intent>? = null
    private var startCount = 0

    private var hasPutTheFloatingView: Boolean = false

    private lateinit var debugger: EasyDebugger

    override fun onDebuggerInitialization(application: Application, debugger: EasyDebugger) {
        this.debugger = debugger
        this.debugger.navigationListeners.add(this)
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
                val service = currentService?.get()
                service?.run {
                    activity.stopService(this)
                }
            }
        }
    }

    override fun onActivityCreated(activity: Activity, bundle: Bundle?) {}

    override fun onActivityResumed(activity: Activity) {}

    private fun placeDebugView(activity: Activity) {
        if (hasPutTheFloatingView)
            return

        if (
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
            && !Settings.canDrawOverlays(activity)
        ) {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                "package:${activity.packageName}".toUri()
            )
            activity.startActivityForResult(intent, PERMISSION_OVERLAY)
            return
        }

        if (
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
            && Settings.canDrawOverlays(activity)
        ) {
            try {
                hasPutTheFloatingView = true
                val service = Intent(activity, FloatingDebugViewService::class.java)
                currentService = WeakReference(service)
                activity.startService(service)
            } catch (e: Exception) {
                hasPutTheFloatingView = false
            }
        }
    }

    companion object {
        private const val PERMISSION_OVERLAY = 2048
    }
}
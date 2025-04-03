package mezzari.torres.lucas.core.service

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.core.net.toUri
import mezzari.torres.lucas.core.archive.isServiceRunning
import mezzari.torres.lucas.core.generic.BaseFloatingViewService
import mezzari.torres.lucas.core.logger.AppLogger
import java.lang.ref.WeakReference
import kotlin.reflect.KClass

/**
 * @author Lucas T. Mezzari
 * @since 25/03/25
 **/
class FloatingWindowManagerImpl(private val appLogger: AppLogger) : FloatingWindowManager {

    private val activeWindows: HashMap<KClass<*>, WeakReference<Intent>> = hashMapOf()

    override fun <T : BaseFloatingViewService> createFloatingWindow(
        context: Context,
        service: KClass<T>,
        intent: Intent?
    ) {
        val mIntent = intent?.apply {
            setClass(context, service.java)
        } ?: Intent(context, service.java)
        if (!isSupported()) {
            appLogger.logMessage("Overlay Permission not supported")
            return
        }
        if (!isPermissionsEnabled(context)) {
            appLogger.logMessage("Overlay Permission not granted")
            requestFloatingWindowPermission(context)
            return
        }
        if (isServiceRunning(context, service.java.name)) {
            appLogger.logMessage("FloatingWindow ($service) already running")
            return
        }
        activeWindows[service] = WeakReference(mIntent)
        context.startService(mIntent)
        appLogger.logMessage("FloatingWindow ($service) started")
    }

    override fun <T : BaseFloatingViewService> destroyFloatingWindow(
        context: Context,
        service: KClass<T>,
        intent: Intent?
    ) {
        val activeIntent = activeWindows[service]?.get()
        val mIntent = intent ?: activeIntent
        if (!isServiceRunning(context, service.java.name)) {
            appLogger.logMessage("FloatingWindow ($service) already stopped")
            return
        }
        if (mIntent == null) {
            val serviceIntent = Intent(context, service.java)
            context.stopService(serviceIntent)
            appLogger.logMessage("FloatingWindow ($service) not found. Attempted to stop with new instance")
            return
        }
        context.stopService(mIntent)
        appLogger.logMessage("FloatingWindow ($service) started")
    }

    private fun isSupported(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }

    @SuppressLint("NewApi")
    private fun isPermissionsEnabled(context: Context): Boolean {
        return Settings.canDrawOverlays(context)
    }

    @SuppressLint("InlinedApi")
    private fun requestFloatingWindowPermission(context: Context) {
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            "package:${context.packageName}".toUri()
        )
        context.startActivity(intent)
        appLogger.logMessage("Overlay Permission requested")
    }
}
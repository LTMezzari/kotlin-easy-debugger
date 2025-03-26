package mezzari.torres.lucas.core.archive

import android.app.ActivityManager
import android.content.Context

/**
 * @author Lucas T. Mezzari
 * @since 22/03/25
 **/
fun isServiceRunning(context: Context, serviceName: String): Boolean {
    val manager =
        context.getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager ?: return false
    for (service in manager.getRunningServices(Int.MAX_VALUE)) {
        if (serviceName == service.service.className) {
            return true
        }
    }
    return false
}
package mezzari.torres.lucas.easy_debugger.navigation.model

import android.app.Activity
import androidx.lifecycle.Lifecycle
import java.lang.ref.WeakReference

/**
 * @author Lucas T. Mezzari
 * @since 16/03/25
 **/
class ActivityWrapper(
    activity: Activity,
    var state: Lifecycle.State
) {
    val activity: WeakReference<Activity> = WeakReference(activity)
}
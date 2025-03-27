package mezzari.torres.lucas.easy_debugger.navigation.model

import android.app.Activity
import androidx.lifecycle.Lifecycle
import mezzari.torres.lucas.core.model.ObservableList

/**
 * @author Lucas T. Mezzari
 * @since 16/03/25
 **/
data class ActivityNavigationStack(
    val activities: ObservableList<ActivityWrapper> = ObservableList()
) {
    fun addActivity(activity: Activity, state: Lifecycle.State = Lifecycle.State.CREATED) {
        if (isActivityInStack(activity)) {
            return
        }
        activities.add(0, ActivityWrapper(activity, state))
    }

    fun removeActivity(activity: Activity) {
        val wrapper = getActivity(activity) ?: return
        activities.remove(wrapper)
    }

    fun getCurrentResumedActivity(): ActivityWrapper? {
        return activities.firstOrNull { it.state == Lifecycle.State.RESUMED && it.isReferenceActive }
    }

    fun updateActivityState(activity: Activity, state: Lifecycle.State) {
        val wrapper = getActivity(activity) ?: return
        val position = activities.indexOf(wrapper)
        activities[position] = ActivityWrapper(activity, state)
    }

    private fun isActivityInStack(activity: Activity): Boolean {
        return getActivity(activity) != null
    }

    private fun getActivity(activity: Activity): ActivityWrapper? {
        return activities.firstOrNull { it.activity.get() == activity }
    }
}
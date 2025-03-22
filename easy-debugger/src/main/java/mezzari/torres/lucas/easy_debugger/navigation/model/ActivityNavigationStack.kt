package mezzari.torres.lucas.easy_debugger.navigation.model

import android.app.Activity
import androidx.lifecycle.Lifecycle
import java.util.Stack

/**
 * @author Lucas T. Mezzari
 * @since 16/03/25
 **/
data class ActivityNavigationStack(
    val activities: Stack<ActivityWrapper> = Stack()
) {
    fun addActivity(activity: Activity, state: Lifecycle.State = Lifecycle.State.CREATED) {
        if (isActivityInStack(activity)) {
            return
        }
        activities.push(ActivityWrapper(activity, state))
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
        wrapper.state = state
    }

    private fun isActivityInStack(activity: Activity): Boolean {
        return getActivity(activity) != null
    }

    private fun getActivity(activity: Activity): ActivityWrapper? {
        return activities.firstOrNull { it.activity.get() == activity }
    }
}
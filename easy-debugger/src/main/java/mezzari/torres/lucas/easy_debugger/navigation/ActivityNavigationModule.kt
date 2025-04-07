package mezzari.torres.lucas.easy_debugger.navigation

import android.app.Application
import androidx.fragment.app.Fragment
import mezzari.torres.lucas.easy_debugger.logger.AppLogger
import mezzari.torres.lucas.easy_debugger.EasyDebugger
import mezzari.torres.lucas.easy_debugger.debug.model.DebugOption
import mezzari.torres.lucas.easy_debugger.debug.model.DebugPageRedirect
import mezzari.torres.lucas.easy_debugger.di.appLogger
import mezzari.torres.lucas.easy_debugger.interfaces.DebuggerModule
import mezzari.torres.lucas.easy_debugger.generics.MinimizedWindow
import mezzari.torres.lucas.easy_debugger.navigation.listener.ActivityNavigationListener
import mezzari.torres.lucas.easy_debugger.navigation.view.fragment.NavigationStackFragment
import mezzari.torres.lucas.easy_debugger.navigation.view.window.NavigationStackWindow

/**
 * @author Lucas T. Mezzari
 * @since 16/03/25
 **/
internal class ActivityNavigationModule(
    private val activityListener: Application.ActivityLifecycleCallbacks? = null,
    private val appLogger: AppLogger,
) : DebuggerModule {

    internal lateinit var listener: ActivityNavigationListener

    override fun onDebuggerInitialization(application: Application, debugger: EasyDebugger) {
        listener = ActivityNavigationListener(
            activityListener,
            debugger.navigationListeners,
            appLogger
        )
        application.registerActivityLifecycleCallbacks(listener)
    }

    override fun onCreateDebugOptions(options: ArrayList<DebugOption>) {
        options.add(
            DebugOption(
                "Activities", DebugPageRedirect(this)
            )
        )
    }

    override fun onCreateDebugFragment(): Fragment {
        return NavigationStackFragment()
    }

    override fun onCreateMinimizedWindow(): MinimizedWindow {
        return NavigationStackWindow()
    }
}

fun EasyDebugger.setActivityNavigationModule(
    activityListener: Application.ActivityLifecycleCallbacks? = null,
) {
    addModule(ActivityNavigationModule(activityListener, appLogger))
}
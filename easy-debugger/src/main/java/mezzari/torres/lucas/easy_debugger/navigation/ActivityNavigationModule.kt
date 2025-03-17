package mezzari.torres.lucas.easy_debugger.navigation

import android.app.Application
import mezzari.torres.lucas.easy_debugger.EasyDebugger
import mezzari.torres.lucas.easy_debugger.interfaces.DebuggerModule
import mezzari.torres.lucas.easy_debugger.navigation.listener.ActivityNavigationListener

/**
 * @author Lucas T. Mezzari
 * @since 16/03/25
 **/
internal class ActivityNavigationModule : DebuggerModule {

    internal lateinit var listener: ActivityNavigationListener

    override fun onDebuggerInitialization(application: Application, debugger: EasyDebugger) {
        listener = ActivityNavigationListener(
            debugger.activityListener,
            debugger.navigationListeners
        )
        application.registerActivityLifecycleCallbacks(listener)
    }
}
package mezzari.torres.lucas.easy_debugger.interfaces

import android.app.Application
import androidx.fragment.app.Fragment
import mezzari.torres.lucas.easy_debugger.EasyDebugger
import mezzari.torres.lucas.easy_debugger.debug.model.DebugOption
import mezzari.torres.lucas.easy_debugger.generics.MinimizedWindow

/**
 * @author Lucas T. Mezzari
 * @since 16/03/25
 **/
interface DebuggerModule {
    fun onDebuggerInitialization(application: Application, debugger: EasyDebugger)

    fun onCreateDebugOptions(options: ArrayList<DebugOption>) {}

    fun onCreateDebugFragment(): Fragment? = null

    fun onCreateMinimizedWindow(): MinimizedWindow? = null

    fun canBeMinimized(): Boolean = onCreateMinimizedWindow() != null
}
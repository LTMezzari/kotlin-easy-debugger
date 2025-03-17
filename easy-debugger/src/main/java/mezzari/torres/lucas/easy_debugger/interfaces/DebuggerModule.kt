package mezzari.torres.lucas.easy_debugger.interfaces

import android.app.Application
import mezzari.torres.lucas.easy_debugger.EasyDebugger
import mezzari.torres.lucas.easy_debugger.debug.dialog.model.DebugOption

/**
 * @author Lucas T. Mezzari
 * @since 16/03/25
 **/
interface DebuggerModule {
    fun onDebuggerInitialization(application: Application, debugger: EasyDebugger)

    fun onCreateDebugOptions(options: ArrayList<DebugOption>) {}
}
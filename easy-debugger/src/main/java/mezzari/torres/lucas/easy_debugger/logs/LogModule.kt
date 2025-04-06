package mezzari.torres.lucas.easy_debugger.logs

import android.app.Application
import androidx.fragment.app.Fragment
import mezzari.torres.lucas.easy_debugger.EasyDebugger
import mezzari.torres.lucas.easy_debugger.debug.model.DebugOption
import mezzari.torres.lucas.easy_debugger.debug.model.DebugPageRedirect
import mezzari.torres.lucas.easy_debugger.di.logListener
import mezzari.torres.lucas.easy_debugger.interfaces.DebuggerModule
import mezzari.torres.lucas.easy_debugger.generics.MinimizedWindow
import mezzari.torres.lucas.easy_debugger.logs.listener.LogListener
import mezzari.torres.lucas.easy_debugger.logs.view.fragment.LogFragment
import mezzari.torres.lucas.easy_debugger.logs.view.window.LogWindow

/**
 * @author Lucas T. Mezzari
 * @since 16/03/25
 **/
internal class LogModule(
    val name: String,
    val logListener: LogListener
) : DebuggerModule {

    override fun onDebuggerInitialization(application: Application, debugger: EasyDebugger) {
        logListener.startListening()
    }

    override fun onCreateDebugOptions(options: ArrayList<DebugOption>) {
        options.add(DebugOption(name, DebugPageRedirect(this)))
    }

    override fun onCreateDebugFragment(): Fragment {
        return LogFragment()
    }

    override fun onCreateMinimizedWindow(): MinimizedWindow {
        return LogWindow()
    }
}

fun EasyDebugger.setLoggerModule(name: String = "Logcat", listener: LogListener = logListener) {
    addModule(LogModule(name, listener))
}
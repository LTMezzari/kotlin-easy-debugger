package mezzari.torres.lucas.easy_debugger_network

import android.app.Application
import androidx.fragment.app.Fragment
import mezzari.torres.lucas.easy_debugger.EasyDebugger
import mezzari.torres.lucas.easy_debugger.debug.model.DebugOption
import mezzari.torres.lucas.easy_debugger.debug.model.DebugPageRedirect
import mezzari.torres.lucas.easy_debugger.interfaces.DebuggerModule
import mezzari.torres.lucas.easy_debugger.generics.MinimizedWindow
import mezzari.torres.lucas.easy_debugger_network.view.fragment.NetworkLoggerFragment
import mezzari.torres.lucas.easy_debugger_network.view.window.NetworkLoggerWindow

/**
 * @author Lucas T. Mezzari
 * @since 16/03/25
 **/
internal class NetworkModule(private val name: String): DebuggerModule {
    override fun onDebuggerInitialization(application: Application, debugger: EasyDebugger) {}

    override fun onCreateDebugOptions(options: ArrayList<DebugOption>) {
        options.add(DebugOption(name, DebugPageRedirect(this)))
    }

    override fun onCreateDebugFragment(): Fragment {
        return NetworkLoggerFragment()
    }

    override fun onCreateMinimizedWindow(): MinimizedWindow {
        return NetworkLoggerWindow()
    }
}

fun EasyDebugger.setNetworkModule(name: String = "Network Calls") {
    addModule(NetworkModule(name))
}
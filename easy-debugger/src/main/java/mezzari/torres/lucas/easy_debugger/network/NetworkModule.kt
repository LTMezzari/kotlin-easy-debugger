package mezzari.torres.lucas.easy_debugger.network

import android.app.Application
import android.content.Intent
import mezzari.torres.lucas.easy_debugger.EasyDebugger
import mezzari.torres.lucas.easy_debugger.debug.dialog.model.DebugOption
import mezzari.torres.lucas.easy_debugger.interfaces.DebuggerModule
import mezzari.torres.lucas.easy_debugger.network.view.NetworkLoggerActivity

/**
 * @author Lucas T. Mezzari
 * @since 16/03/25
 **/
class NetworkModule : DebuggerModule {
    override fun onDebuggerInitialization(application: Application, debugger: EasyDebugger) {}

    override fun onCreateDebugOptions(options: ArrayList<DebugOption>) {
        options.add(DebugOption("Network Logs") { context ->
            context.startActivity(Intent(context, NetworkLoggerActivity::class.java))
        })
    }
}
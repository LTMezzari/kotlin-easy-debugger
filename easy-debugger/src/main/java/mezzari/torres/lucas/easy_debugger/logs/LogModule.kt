package mezzari.torres.lucas.easy_debugger.logs

import android.app.Application
import android.content.Intent
import mezzari.torres.lucas.easy_debugger.EasyDebugger
import mezzari.torres.lucas.easy_debugger.debug.dialog.model.DebugOption
import mezzari.torres.lucas.easy_debugger.interfaces.DebuggerModule
import mezzari.torres.lucas.easy_debugger.logs.view.LogActivity

/**
 * @author Lucas T. Mezzari
 * @since 16/03/25
 **/
internal class LogModule : DebuggerModule {
    override fun onDebuggerInitialization(application: Application, debugger: EasyDebugger) {
        debugger.logListener.startListening()
    }

    override fun onCreateDebugOptions(options: ArrayList<DebugOption>) {
        options.add(DebugOption("App Logs") { context ->
            context.startActivity(Intent(context, LogActivity::class.java))
        })
    }
}
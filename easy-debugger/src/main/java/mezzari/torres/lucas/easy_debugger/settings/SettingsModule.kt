package mezzari.torres.lucas.easy_debugger.settings

import android.app.Application
import android.content.Intent
import android.provider.Settings
import mezzari.torres.lucas.easy_debugger.EasyDebugger
import mezzari.torres.lucas.easy_debugger.debug.dialog.model.DebugOption
import mezzari.torres.lucas.easy_debugger.interfaces.DebuggerModule

/**
 * @author Lucas T. Mezzari
 * @since 16/03/25
 **/
internal class SettingsModule : DebuggerModule {
    override fun onDebuggerInitialization(application: Application, debugger: EasyDebugger) {}

    override fun onCreateDebugOptions(options: ArrayList<DebugOption>) {
        options.add(DebugOption("Settings") { context ->
            context.startActivity(Intent(Settings.ACTION_SETTINGS))
        })
    }
}
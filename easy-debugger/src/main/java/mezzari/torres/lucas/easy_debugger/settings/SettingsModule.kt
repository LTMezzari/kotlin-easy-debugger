package mezzari.torres.lucas.easy_debugger.settings

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import mezzari.torres.lucas.easy_debugger.EasyDebugger
import mezzari.torres.lucas.easy_debugger.debug.dialog.model.DebugOption
import mezzari.torres.lucas.easy_debugger.interfaces.DebuggerModule

/**
 * @author Lucas T. Mezzari
 * @since 16/03/25
 **/
internal class SettingsModule : DebuggerModule {

    private lateinit var packageName: String

    override fun onDebuggerInitialization(application: Application, debugger: EasyDebugger) {
        packageName = application.packageName
    }

    override fun onCreateDebugOptions(options: ArrayList<DebugOption>) {
        options.add(DebugOption("Settings") { context ->
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.setData(Uri.fromParts("package", packageName, null))
            context.startActivity(intent)
        })
    }
}
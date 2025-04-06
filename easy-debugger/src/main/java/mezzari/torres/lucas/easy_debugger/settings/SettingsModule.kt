package mezzari.torres.lucas.easy_debugger.settings

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import mezzari.torres.lucas.easy_debugger.EasyDebugger
import mezzari.torres.lucas.easy_debugger.debug.model.DebugOption
import mezzari.torres.lucas.easy_debugger.interfaces.DebuggerModule

/**
 * @author Lucas T. Mezzari
 * @since 16/03/25
 **/
internal class SettingsModule(private val name: String) : DebuggerModule {

    private lateinit var packageName: String

    override fun onDebuggerInitialization(application: Application, debugger: EasyDebugger) {
        packageName = application.packageName
    }

    override fun onCreateDebugOptions(options: ArrayList<DebugOption>) {
        options.add(DebugOption(name) { context ->
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.setData(Uri.fromParts("package", packageName, null))
            context.startActivity(intent)
        })
    }
}

fun EasyDebugger.setSettingsModule(name: String = "App Settings") {
    addModule(SettingsModule(name))
}
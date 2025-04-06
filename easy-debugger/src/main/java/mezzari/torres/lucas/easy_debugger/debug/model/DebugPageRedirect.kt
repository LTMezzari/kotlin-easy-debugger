package mezzari.torres.lucas.easy_debugger.debug.model

import android.content.Context
import android.content.Intent
import mezzari.torres.lucas.easy_debugger.EasyDebugger
import mezzari.torres.lucas.easy_debugger.debug.DebugModule
import mezzari.torres.lucas.easy_debugger.debug.view.DebugActivity
import mezzari.torres.lucas.easy_debugger.interfaces.DebuggerModule

/**
 * @author Lucas T. Mezzari
 * @since 22/03/25
 **/
class DebugPageRedirect(
    private val module: DebuggerModule,
) : DebugDialogCallback {
    override fun onOptionClick(context: Context) {
        val debugModule = EasyDebugger.instance.getModuleByType<DebugModule>()
        debugModule?.currentActiveModule = module
        context.startActivity(Intent(context, DebugActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }
}
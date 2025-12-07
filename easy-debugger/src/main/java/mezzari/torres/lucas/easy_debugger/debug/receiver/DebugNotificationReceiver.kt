package mezzari.torres.lucas.easy_debugger.debug.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import mezzari.torres.lucas.easy_debugger.EasyDebugger
import mezzari.torres.lucas.easy_debugger.debug.DebugModule
import mezzari.torres.lucas.easy_debugger.debug.view.dialog.DebugDialog

/**
 * @author Lucas T. Mezzari
 * @since 07/12/25
 **/
class DebugNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val debugger = EasyDebugger.instance
        val module =
            debugger.getModuleByType<DebugModule>() ?: return
        val activity =
            module.currentActivity as? AppCompatActivity
                ?: return
        DebugDialog(debugger.debugOptions).show(
            activity.supportFragmentManager,
            DebugDialog::class.java.name
        )
    }
}
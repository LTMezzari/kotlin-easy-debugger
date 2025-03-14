package mezzari.torres.lucas.generic

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import mezzari.torres.lucas.view.DebugDialog

/**
 * @author Lucas T. Mezzari
 * @author lucas.mezzari@operacao.rcadigital.com.br
 * @since 2020-02-21
 */
abstract class BaseActivity: AppCompatActivity() {

    private val debugReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            context?.let {
                onDebugReceived(context)
            }
            abortBroadcast()
        }
    }

    override fun onResume() {
        super.onResume()
//        registerReceiver(debugReceiver, IntentFilter("DEBUG_DIALOG"))
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(debugReceiver)
    }

    open fun onDebugReceived(context: Context) {
        DebugDialog().show(supportFragmentManager, DebugDialog::class.java.name)
    }
}
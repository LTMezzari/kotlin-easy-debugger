package mezzari.torres.lucas.core.record.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import mezzari.torres.lucas.core.record.ScreenRecordManager

/**
 * @author Lucas T. Mezzari
 * @since 31/03/25
 **/
class OnScreenRecordStoppedReceiver: BroadcastReceiver() {

    private val recordManager: ScreenRecordManager? = null

    override fun onReceive(context: Context?, intent: Intent?) {
        recordManager?.stopRecording()
    }
}
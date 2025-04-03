package mezzari.torres.lucas.easy_debugger.record.contract

import android.content.Context
import android.content.Intent
import android.media.projection.MediaProjectionManager
import androidx.activity.result.contract.ActivityResultContract

/**
 * @author Lucas T. Mezzari
 * @since 31/03/25
 **/
class ScreenRecordContract(private val mediaProjectionManager: MediaProjectionManager) :
    ActivityResultContract<Any?, Pair<Int, Intent?>>() {
    override fun createIntent(context: Context, input: Any?): Intent =
        mediaProjectionManager.createScreenCaptureIntent()

    override fun parseResult(resultCode: Int, intent: Intent?): Pair<Int, Intent?> =
        Pair(resultCode, intent)
}
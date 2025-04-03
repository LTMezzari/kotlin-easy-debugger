package mezzari.torres.lucas.easy_debugger.record.manager

import android.content.Context
import android.content.Intent
import android.media.projection.MediaProjectionManager
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import mezzari.torres.lucas.core.logger.AppLogger
import mezzari.torres.lucas.easy_debugger.record.contract.ScreenRecordContract
import mezzari.torres.lucas.easy_debugger.record.service.ScreenRecordService
import java.lang.ref.WeakReference

/**
 * @author Lucas T. Mezzari
 * @since 29/03/25
 **/
class ScreenRecordManagerImpl(private val appLogger: AppLogger) : ScreenRecordManager {

    private lateinit var mediaProjectionManager: MediaProjectionManager
    private var launcher: ActivityResultLauncher<Any?>? = null
    private var service: WeakReference<Intent>? = null

    private var applicationContext: Context? = null

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        if (owner is FragmentActivity) {
            mediaProjectionManager =
                owner.getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
            launcher =
                owner.registerForActivityResult(ScreenRecordContract(mediaProjectionManager)) { (resultCode, data) ->
                    onActivityResult(resultCode, data)
                }
        }
    }

    override fun startRecording(activity: FragmentActivity) {
        applicationContext = activity.applicationContext
        appLogger.logMessage("Starting ScreenRecord Service")
        launcher?.launch(null)
    }

    override fun stopRecording() {
        appLogger.logMessage("Stop ScreenRecord Service")
        applicationContext?.run {
            stopService(this)
        }
    }

    private fun onActivityResult(resultCode: Int, data: Intent?) {
        try {
            applicationContext?.run {
                startService(this, resultCode, data)
            }
        } catch (e: Exception) {
            appLogger.logError(e)
        }
    }

    private fun startService(context: Context, resultCode: Int, data: Intent?) {
        val service = Intent(context, ScreenRecordService::class.java).apply {
            putExtra("resultCode", resultCode)
            putExtra("data", data)
        }
        this.service = WeakReference(service)
        context.startService(service)
    }

    private fun stopService(context: Context) {
        val service = service?.get() ?: Intent(context, ScreenRecordService::class.java)
        context.stopService(service)
    }
}
package mezzari.torres.lucas.core.record

import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.hardware.display.DisplayManager
import android.hardware.display.VirtualDisplay
import android.media.MediaRecorder
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.os.Build
import android.util.DisplayMetrics
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.NotificationCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import mezzari.torres.lucas.core.R
import mezzari.torres.lucas.core.di.fileManager
import mezzari.torres.lucas.core.di.notificationDispatcher
import mezzari.torres.lucas.core.file.FileManager
import mezzari.torres.lucas.core.logger.AppLogger
import mezzari.torres.lucas.core.notification.NotificationDispatcher
import mezzari.torres.lucas.core.record.contract.ScreenRecordContract
import mezzari.torres.lucas.core.record.receiver.OnScreenRecordStoppedReceiver

/**
 * @author Lucas T. Mezzari
 * @since 29/03/25
 **/
class ScreenRecordManagerImpl(private val appLogger: AppLogger) : ScreenRecordManager {

    private val mFileManager: FileManager by lazy { fileManager }
    private val mNotificationDispatcher: NotificationDispatcher by lazy { notificationDispatcher }

    private var displayMetrics: DisplayMetrics = DisplayMetrics()
//    private lateinit var virtualDisplay: VirtualDisplay
//    private lateinit var mediaProjection: MediaProjection
//    private lateinit var mediaProjectionManager: MediaProjectionManager
    private var mediaRecorder: MediaRecorder? = null
    private var launcher: ActivityResultLauncher<Any?>? = null

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        if (owner is FragmentActivity) {
            initializeProperties(owner)
//            launcher =
//                owner.registerForActivityResult(ScreenRecordContract(mediaProjectionManager)) { (resultCode, data) ->
//                    onActivityResult(resultCode, data)
//                    startRecording(owner)
//                }
        }
    }

    override fun startRecording(activity: FragmentActivity) {
        appLogger.logMessage("Starting ScreenRecord")
        if (mediaRecorder == null) {
            prepare(activity)
        }
        try {
//            if (!::mediaProjection.isInitialized) {
//                launcher?.launch(null)
//                return
//            }
//            virtualDisplay = getVirtualDisplay() ?: return
            mediaRecorder?.start()
            showNotification(activity)
        } catch (e: Exception) {
            appLogger.logError(e)
            stopRecording()
        }
    }

    override fun stopRecording() {
        appLogger.logMessage("Stop ScreenRecord")
        try {
            mediaRecorder?.stop()
            mediaRecorder?.release()
            mediaRecorder = null
        } catch (e: Exception) {
            appLogger.logError(e)
        }
    }

    private fun onActivityResult(resultCode: Int, data: Intent?) {
        try {
            if (data == null) {
                return
            }
//            mediaProjection = mediaProjectionManager.getMediaProjection(resultCode, data)
        } catch (e: Exception) {
            appLogger.logError(e)
        }
    }

    private fun prepare(activity: Activity) {
        try {
            appLogger.logMessage("Preparing ScreenRecord")
            val mediaRecorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                MediaRecorder(activity)
            } else {
                MediaRecorder()
            }

            val file = mFileManager.createFile(
                activity.getExternalFilesDir("recordings") ?: activity.filesDir,
                "${mFileManager.getFileName()}.mp4"
            )

            val width = displayMetrics.widthPixels
            val height = displayMetrics.heightPixels

            mediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE)
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264)
            mediaRecorder.setVideoEncodingBitRate(VIDEO_ENCODING_BIT_RATE)
            mediaRecorder.setVideoFrameRate(VIDEO_FRAME_RATE)
            mediaRecorder.setVideoSize(width, height)
            mediaRecorder.setOutputFile(file.path)
            mediaRecorder.prepare()
            this.mediaRecorder = mediaRecorder
        } catch (e: Exception) {
            appLogger.logError(e)
        }
    }

    private fun initializeProperties(activity: Activity) {
        try {
            appLogger.logMessage("Initializing ScreenRecord")
//            mediaProjectionManager =
//                activity.getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                activity.display.getMetrics(displayMetrics)
            } else {
                activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
            }
        } catch (e: Exception) {
            appLogger.logError(e)
        }
    }

//    private fun getVirtualDisplay(): VirtualDisplay? {
//        appLogger.logMessage("Fetching Virtual Display")
//        val mediaRecorder =
//            this.mediaRecorder ?: throw Exception("ScreenRecordManager should be prepared first")
//        val screenDensity = displayMetrics.densityDpi
//        val width = displayMetrics.widthPixels
//        val height = displayMetrics.heightPixels
//        return mediaProjection.createVirtualDisplay(
//            this::class.java.simpleName,
//            width,
//            height,
//            screenDensity,
//            DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
//            mediaRecorder.surface,
//            null,
//            null
//        )
//    }

    private fun showNotification(activity: FragmentActivity) {
        appLogger.logMessage("Showing Recording Notification")
        val snoozeIntent = Intent(activity, OnScreenRecordStoppedReceiver::class.java).apply {
            action = ACTION_STOP
            flags += PendingIntent.FLAG_IMMUTABLE
        }
        val snoozePendingIntent: PendingIntent =
            PendingIntent.getBroadcast(activity, 0, snoozeIntent, PendingIntent.FLAG_IMMUTABLE)
        val notification = NotificationCompat.Builder(activity, NotificationDispatcher.DEFAULT_CHANNEL)
            .setSmallIcon(android.R.mipmap.sym_def_app_icon)
            .setContentTitle("Recording is in progress")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .addAction(
                android.R.drawable.ic_popup_sync, "Stop",
                snoozePendingIntent
            ).build()
        mNotificationDispatcher.sendNotification(activity, notification, NOTIFICATION_ID)
    }

    companion object {
        const val VIDEO_ENCODING_BIT_RATE = 512 * 1000
        const val VIDEO_FRAME_RATE = 30
        const val ACTION_STOP = "STOP"
        const val NOTIFICATION_ID = 3123123
    }
}
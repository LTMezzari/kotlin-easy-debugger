package mezzari.torres.lucas.easy_debugger.record.service

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.hardware.display.DisplayManager
import android.hardware.display.VirtualDisplay
import android.media.MediaRecorder
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.DisplayMetrics
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import mezzari.torres.lucas.easy_debugger.file.manager.FileManager
import mezzari.torres.lucas.easy_debugger.generic.BaseService
import mezzari.torres.lucas.easy_debugger.logger.AppLogger
import mezzari.torres.lucas.easy_debugger.notification.NotificationDispatcher
import mezzari.torres.lucas.easy_debugger.EasyDebugger
import mezzari.torres.lucas.easy_debugger.di.appLogger
import mezzari.torres.lucas.easy_debugger.di.fileManager
import mezzari.torres.lucas.easy_debugger.di.notificationDispatcher
import mezzari.torres.lucas.easy_debugger.file.FileProviderConfiguration
import mezzari.torres.lucas.easy_debugger.record.ScreenRecordModule
import mezzari.torres.lucas.easy_debugger.record.receiver.OnScreenRecordStoppedReceiver

/**
 * @author Lucas T. Mezzari
 * @since 02/04/25
 **/
internal class ScreenRecordService : BaseService() {

    private val mFileManager: FileManager by lazy { fileManager }
    private val mAppLogger: AppLogger by lazy { appLogger }
    private val dispatcher: NotificationDispatcher by lazy { notificationDispatcher }

    private var displayMetrics: DisplayMetrics = DisplayMetrics()
    private lateinit var virtualDisplay: VirtualDisplay
    private lateinit var mediaProjection: MediaProjection
    private lateinit var mediaProjectionManager: MediaProjectionManager
    private var mediaRecorder: MediaRecorder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mAppLogger.logMessage("Starting ScreenRecord")
        try {
            showNotification(applicationContext)
            initializeProperties(applicationContext, intent)
            prepare(applicationContext)
            mediaRecorder?.start()
            virtualDisplay = getVirtualDisplay() ?: return START_STICKY
        } catch (e: Exception) {
            mAppLogger.logError(e)
            dispatcher.cancelNotification(applicationContext, NOTIFICATION_ID)
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        mAppLogger.logMessage("Stop ScreenRecord")
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                stopForeground(STOP_FOREGROUND_REMOVE)
            } else {
                stopForeground(true)
            }
            mediaRecorder?.stop()
            mediaRecorder?.release()
            virtualDisplay.release()
            mediaProjection.stop()
            mediaRecorder = null
        } catch (e: Exception) {
            mAppLogger.logError(e)
        }
    }

    private fun prepare(context: Context) {
        try {
            mAppLogger.logMessage("Preparing ScreenRecord")

            val parentFile = getFileConfiguration()?.recordConfiguration?.getParentDir(context)
                ?: throw Exception("Parent file was not created")

            val mediaRecorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                MediaRecorder(context)
            } else {
                MediaRecorder()
            }

            val file = mFileManager.createFile(
                parentFile,
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
            mAppLogger.logError(e)
        }
    }

    private fun initializeProperties(context: Context, intent: Intent?) {
        try {
            if (intent == null) {
                return
            }
            mAppLogger.logMessage("Initializing ScreenRecord")
            mediaProjectionManager =
                getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
            ContextCompat.getDisplayOrDefault(context).getMetrics(displayMetrics)
            val resultCode = intent.getIntExtra("resultCode", 9999).takeIf { it != 9999 } ?: return
            val data: Intent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra("data", Intent::class.java) ?: return
            } else {
                intent.getParcelableExtra("data") ?: return
            }
            mediaProjection = mediaProjectionManager.getMediaProjection(resultCode, data)
            val handler = Handler(Looper.getMainLooper())
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                mediaProjection.registerCallback(object : MediaProjection.Callback() {
                    override fun onStop() {
                        super.onStop()
                    }
                }, handler)
                return
            }
            mediaProjection.registerCallback(object : MediaProjection.Callback() {}, handler)
        } catch (e: Exception) {
            mAppLogger.logError(e)
        }
    }

    private fun getVirtualDisplay(): VirtualDisplay? {
        mAppLogger.logMessage("Fetching Virtual Display")
        val mediaRecorder =
            this.mediaRecorder ?: throw Exception("ScreenRecordManager should be prepared first")
        val screenDensity = displayMetrics.densityDpi
        val width = displayMetrics.widthPixels
        val height = displayMetrics.heightPixels
        return mediaProjection.createVirtualDisplay(
            this::class.java.simpleName,
            width,
            height,
            screenDensity,
            DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
            mediaRecorder.surface,
            null,
            null
        )
    }

    private fun showNotification(context: Context) {
        mAppLogger.logMessage("Showing Recording Notification")
        val snoozeIntent = Intent(context, OnScreenRecordStoppedReceiver::class.java).apply {
            action = ACTION_STOP
            flags += PendingIntent.FLAG_IMMUTABLE
        }
        val snoozePendingIntent: PendingIntent =
            PendingIntent.getBroadcast(context, 0, snoozeIntent, PendingIntent.FLAG_IMMUTABLE)
        val notification =
            NotificationCompat.Builder(context, NotificationDispatcher.DEFAULT_CHANNEL)
                .setSmallIcon(android.R.mipmap.sym_def_app_icon)
                .setContentTitle("Recording is in progress")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(
                    android.R.drawable.ic_popup_sync, "Stop",
                    snoozePendingIntent
                ).build()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(
                NOTIFICATION_ID,
                notification,
                ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PROJECTION
            )
            return
        }
        startForeground(NOTIFICATION_ID, notification)
    }

    private fun getScreenRecordModule(): ScreenRecordModule? {
        return EasyDebugger.instance.getModuleByType<ScreenRecordModule>()
    }

    private fun getFileConfiguration(): FileProviderConfiguration? {
        return getScreenRecordModule()?.fileProviderConfiguration
    }

    companion object {
        const val VIDEO_ENCODING_BIT_RATE = 512 * 1000
        const val VIDEO_FRAME_RATE = 30
        const val ACTION_STOP = "STOP"
        const val NOTIFICATION_ID = 3123123
    }
}
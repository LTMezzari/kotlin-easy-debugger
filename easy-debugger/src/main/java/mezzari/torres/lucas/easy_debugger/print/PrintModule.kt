package mezzari.torres.lucas.easy_debugger.print

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import androidx.core.content.FileProvider
import mezzari.torres.lucas.easy_debugger.EasyDebugger
import mezzari.torres.lucas.easy_debugger.interfaces.DebuggerModule
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.core.graphics.createBitmap
import mezzari.torres.lucas.core.logger.AppLogger
import mezzari.torres.lucas.easy_debugger.debug.model.DebugOption
import mezzari.torres.lucas.easy_debugger.di.appLogger

/**
 * @author Lucas T. Mezzari
 * @since 17/03/25
 **/
class PrintModule(private val name: String, private val appLogger: AppLogger) : DebuggerModule,
    Application.ActivityLifecycleCallbacks {

    private var currentActivity: Activity? = null

    override fun onDebuggerInitialization(application: Application, debugger: EasyDebugger) {
        debugger.navigationListeners.add(this)
    }

    override fun onCreateDebugOptions(options: ArrayList<DebugOption>) {
        options.add(
            DebugOption(
                name
            ) {
                val activity = currentActivity ?: return@DebugOption
                takeScreenshot(activity)
            }
        )
    }

    private fun takeScreenshot(activity: Activity) {
        try {
            val rootView = activity.window.decorView.rootView
            val print = createBitmap(rootView.width, rootView.height)
            val canvas = Canvas(print)
            rootView.draw(canvas)
            val file = createFile(activity) ?: return
            writeFile(print, file)
            sharePrint(activity, file)
        } catch (e: Exception) {
            appLogger.logError(e)
        }
    }

    private fun createFile(activity: Activity): File? {
        return try {
            val name = SimpleDateFormat("yyyy-MM-dd_hh-mm-ss", Locale.getDefault()).format(Date())
            File(activity.cacheDir, "$name.jpg")
        } catch (e: java.lang.Exception) {
            appLogger.logError(e)
            null
        }
    }

    private fun writeFile(print: Bitmap, file: File) {
        val outputStream = FileOutputStream(file)
        print.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
    }

    private fun sharePrint(activity: Activity, file: File) {
        activity.startActivity(
            Intent.createChooser(
                Intent(Intent.ACTION_SEND).apply {
                    val uri = FileProvider.getUriForFile(
                        activity,
                        "mezzari.torres.lucas.easy_debugger.library.provider",
                        file
                    )
                    setDataAndType(uri, "image/*")
                    putExtra(
                        Intent.EXTRA_STREAM,
                        uri
                    )
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                },
                "Sharing with"
            )
        )
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}

    override fun onActivityStarted(activity: Activity) {}

    override fun onActivityResumed(activity: Activity) {
        if (currentActivity == activity) {
            return
        }
        currentActivity = activity
    }

    override fun onActivityPaused(activity: Activity) {}

    override fun onActivityStopped(activity: Activity) {
        if (currentActivity != activity) {
            return
        }
        currentActivity = null
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

    override fun onActivityDestroyed(activity: Activity) {}
}

fun EasyDebugger.setPrintModule(name: String = "Print") {
    addModule(PrintModule(name, appLogger))
}
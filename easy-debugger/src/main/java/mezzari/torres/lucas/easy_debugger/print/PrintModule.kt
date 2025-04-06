package mezzari.torres.lucas.easy_debugger.print

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import mezzari.torres.lucas.easy_debugger.EasyDebugger
import mezzari.torres.lucas.easy_debugger.interfaces.DebuggerModule
import java.io.File
import java.io.FileOutputStream
import mezzari.torres.lucas.core.di.fileManager
import mezzari.torres.lucas.core.di.printScreenManager
import mezzari.torres.lucas.core.file.FileManager
import mezzari.torres.lucas.core.logger.AppLogger
import mezzari.torres.lucas.core.print.PrintScreenManager
import mezzari.torres.lucas.easy_debugger.debug.model.DebugOption
import mezzari.torres.lucas.easy_debugger.di.appLogger
import mezzari.torres.lucas.easy_debugger.file.FileProviderConfiguration

/**
 * @author Lucas T. Mezzari
 * @since 17/03/25
 **/
class PrintModule(
    private val name: String,
    private val appLogger: AppLogger,
    private val fileConfiguration: FileProviderConfiguration
) : DebuggerModule,
    Application.ActivityLifecycleCallbacks {

    private val mFileManager: FileManager by lazy { fileManager }
    private val mPrintManager: PrintScreenManager by lazy { printScreenManager }

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
            val file = createFile(activity) ?: return
            writeFile(mPrintManager.printActivity(activity), file)
            sharePrint(activity, file)
        } catch (e: Exception) {
            appLogger.logError(e)
        }
    }

    private fun createFile(activity: Activity): File? {
        return try {
            val parentFile =
                fileConfiguration.printConfiguration.getParentDir(activity)
                    ?: throw Exception("Parent was not created")
            mFileManager.createFile(
                parentFile,
                "${mFileManager.getFileName()}.jpg"
            )
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
                    val uri =
                        mFileManager.getUriForFile(activity, file, fileConfiguration.authority)
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
    val configuration = configuration.fileProviderConfiguration
        ?: throw Exception("A File Provider Configuration should be setup for Print Module to work")
    addModule(PrintModule(name, appLogger, configuration))
}
package mezzari.torres.lucas.core.logger

import android.util.Log
import mezzari.torres.lucas.core.BuildConfig
import mezzari.torres.lucas.core.model.Configuration

/**
 * @author Lucas T. Mezzari
 * @since 25/03/25
 **/
class AppLoggerImpl(private val configuration: Configuration) : AppLogger {

    private val isLogsEnabled get() = configuration.isLogsEnabled

    override fun logMessage(message: String?) {
        if (!isLogsEnabled) {
            return
        }
        val tag = this.tag
        val m = message ?: return
        Log.d(tag, m)
    }

    override fun logError(e: Exception?) {
        if (!isLogsEnabled) {
            return
        }
        e?.printStackTrace()
        val tag = this.tag
        val error = e ?: return
        Log.e(tag, error.toString())
    }

    private val ignoredClasses: List<String> = listOf(
        AppLoggerImpl::class.java.name,
    )

    private val tag: String
        get() {
            if (BuildConfig.DEBUG) {
                return removePackage(
                    Throwable().stackTrace
                        .first { it.className !in ignoredClasses }
                        .className
                )
            }
            return "EasyDebugger"
        }

    private fun removePackage(className: String): String {
        val splitName = className.split(".")
        if (splitName.isEmpty()) {
            return className
        }
        return splitName.last()
    }
}
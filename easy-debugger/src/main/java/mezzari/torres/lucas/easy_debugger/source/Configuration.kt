package mezzari.torres.lucas.easy_debugger.source

import mezzari.torres.lucas.core.logger.AppLoggerImpl
import mezzari.torres.lucas.easy_debugger.file.FileProviderConfiguration

/**
 * @author Lucas T. Mezzari
 * @since 25/03/25
 **/
data class Configuration(
    var isLogsEnabled: Boolean = false,
    var fileProviderConfiguration: FileProviderConfiguration? = null,
) : AppLoggerImpl.LoggerFlag {
    override val isEnabled: Boolean
        get() = isLogsEnabled
}
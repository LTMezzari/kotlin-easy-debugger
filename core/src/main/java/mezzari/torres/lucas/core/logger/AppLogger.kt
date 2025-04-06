package mezzari.torres.lucas.core.logger

/**
 * @author Lucas T. Mezzari
 * @since 25/03/25
 **/
interface AppLogger {
    fun logMessage(message: String?)

    fun logError(e: Exception?)
}
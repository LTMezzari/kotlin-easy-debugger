package mezzari.torres.lucas.easy_debugger.logs.model

/**
 * @author Lucas T. Mezzari
 * @since 16/03/25
 **/
data class AppLog(private val log: String) {
    private val timestamp: String
    private val processId: String
    private val tag: String
    private val packageName: String
    private val process: String
    private val level: String
    private val message: String

    init {
        val splitLog = log.split(" ")
        timestamp = splitLog[0]
        processId = splitLog[1]
        tag = splitLog[2]
        packageName = splitLog[3]
        process = splitLog[4]
        level = splitLog[5]
        message = splitLog[6]
    }
}
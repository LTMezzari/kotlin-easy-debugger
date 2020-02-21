package mezzari.torres.lucas.easy_debugger.model

/**
 * @author Lucas T. Mezzari
 * @since 19/02/2020
 */
class NetworkLog(
    val url: String,
    val request: NetworkRequest,
    val response: NetworkResponse
)
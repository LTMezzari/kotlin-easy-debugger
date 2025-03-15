package mezzari.torres.lucas.easy_debugger.network.model

import okhttp3.Headers
import org.json.JSONObject
import java.lang.Exception

/**
 * @author Lucas T. Mezzari
 * @since 19/02/2020
 */
class NetworkLog(
    val url: String,
    val request: NetworkRequest,
    val response: NetworkResponse?,
    val wasSuccessful: Boolean,
    val error: Exception? = null
) {
    fun stringifyRequest(): String? {
        var result = ""
        result += stringifyHeaders(request.headers)
        result += stringify(request.body, "Body")

        return if (result.isEmpty()) {
            null
        } else {
            result.trim()
        }
    }

    fun stringifyResponse(): String? {
        if (response != null){
            var result = ""
            result += stringifyHeaders(response.headers)
            result += stringify(response.response, "Response")

            return if (result.isEmpty()) {
                null
            } else {
                result.trim()
            }
        }

        return null
    }

    private fun stringifyHeaders(headers: Headers): String {
        var result = ""
        for (i in 0 until headers.size()) {
            result += String.format("%s: %s\n", headers.name(i), headers.value(i))
        }
        return if (result.isNotEmpty()) {
            "Headers\n\n${result.trim()}"
        } else {
            ""
        }
    }

    private fun stringify(body: String?, title: String): String {
        if (body == null)
            return ""

        return try {
            "\n\n$title\n\n${JSONObject(body).toString(4)}"
        } catch (e: Exception) {
            ""
        }
    }
}
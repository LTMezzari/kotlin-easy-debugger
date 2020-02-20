package mezzari.torres.lucas.easy_debugger.model

import okhttp3.Headers
import okhttp3.ResponseBody
import okio.Buffer
import kotlin.text.Charsets.UTF_8

/**
 * @author Lucas T. Mezzari
 * @since 19/02/2020
 */
class NetworkResponse(
    val code: Int,
    val headers: Headers,
    val response: String?
) {

    override fun toString(): String {
        return "{" +
                "\n\tcode: " + code +
                "\n\theaders: " + headers.toString() +
                "\n\tresponse: " + response +
                "\n}"
    }
}
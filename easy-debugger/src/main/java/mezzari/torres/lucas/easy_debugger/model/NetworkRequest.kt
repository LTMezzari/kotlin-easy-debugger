package mezzari.torres.lucas.easy_debugger.model

import okhttp3.Headers
import okhttp3.RequestBody
import okio.Buffer

/**
 * @author Lucas T. Mezzari
 * @since 19/02/2020
 */
class NetworkRequest (
    val method: String,
    val headers: Headers,
    val body: RequestBody?
) {

    private fun stringBody(): String {
        val buffer = Buffer()
        body?.writeTo(buffer)
        return buffer.readUtf8()
    }

    override fun toString(): String {
        return "{" +
                "\n\tmethod: " + method +
                "\n\theaders: " + headers.toString() +
                "\n\tbody: " + stringBody() +
                "\n}"
    }
}
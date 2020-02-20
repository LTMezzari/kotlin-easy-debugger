package mezzari.torres.lucas.easy_debugger.model

import okhttp3.Headers
import okhttp3.ResponseBody

/**
 * @author Lucas T. Mezzari
 * @since 19/02/2020
 */
class NetworkResponse(
    val code: Int,
    val headers: Headers,
    val response: ResponseBody?
) {
    override fun toString(): String {
        return "{" +
                "\n\tcode: " + code +
                "\n\theaders: " + headers.toString() +
                "\n\tresponse: " + response.toString() +
                "\n}"
    }
}
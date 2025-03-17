package mezzari.torres.lucas.easy_debugger_network.model

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
)
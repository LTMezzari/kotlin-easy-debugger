package mezzari.torres.lucas.easy_debugger.network.model

import okhttp3.Headers
import okhttp3.RequestBody
import okio.Buffer
import kotlin.text.Charsets.UTF_8

/**
 * @author Lucas T. Mezzari
 * @since 19/02/2020
 */
class NetworkRequest (
    val method: String,
    val headers: Headers,
    val body: String? = null
)
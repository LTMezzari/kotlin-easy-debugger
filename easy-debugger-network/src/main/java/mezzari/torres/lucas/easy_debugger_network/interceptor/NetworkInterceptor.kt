package mezzari.torres.lucas.easy_debugger_network.interceptor

import mezzari.torres.lucas.core.model.ObservableList
import mezzari.torres.lucas.easy_debugger_network.model.NetworkLog
import mezzari.torres.lucas.easy_debugger_network.model.NetworkRequest
import mezzari.torres.lucas.easy_debugger_network.model.NetworkResponse
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okio.Buffer
import java.nio.charset.Charset
import kotlin.text.Charsets.UTF_8

/**
 * @author Lucas T. Mezzari
 * @since 29/07/2020
 */
class NetworkInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url.toUrl().toString()

        val networkRequest = buildRequestLogs(request)

        val response: Response
        try {
            response = chain.proceed(request)

            val networkResponse = buildResponseLogs(response)

            networkLogs += NetworkLog(url, networkRequest, networkResponse, response.isSuccessful)
        } catch (e: Exception) {
            networkLogs += NetworkLog(url, networkRequest, null, false, e)
            throw e
        }

        return response
    }

    private fun buildRequestLogs(request: Request): NetworkRequest {
        val method = request.method
        val headers = request.headers
        val requestBody = request.body

        val body = if (requestBody != null) {
            val buffer = Buffer()
            requestBody.writeTo(buffer)
            val contentType = requestBody.contentType()
            val charset: Charset = contentType?.charset(UTF_8) ?: UTF_8
            buffer.readString(charset)
        } else {
            null
        }

        return NetworkRequest(method, headers, body)
    }

    private fun buildResponseLogs(response: Response): NetworkResponse {
        val code = response.code
        val headers = response.headers
        val responseBody = response.body

        val body = if (responseBody != null) {
            val source = responseBody.source()
            source.request(Long.MAX_VALUE)
            val buffer = source.buffer

            val contentType = responseBody.contentType()
            val charset: Charset = contentType?.charset(UTF_8) ?: UTF_8

            buffer.clone().readString(charset)
        } else {
            null
        }

        return NetworkResponse(code, headers, body)
    }

    companion object {
        val networkLogs: ObservableList<NetworkLog> = ObservableList()
    }
}
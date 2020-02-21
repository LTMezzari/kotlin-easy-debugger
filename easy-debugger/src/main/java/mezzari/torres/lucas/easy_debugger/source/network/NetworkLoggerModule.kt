package mezzari.torres.lucas.easy_debugger.source.network

import mezzari.torres.lucas.easy_debugger.model.NetworkLog
import mezzari.torres.lucas.easy_debugger.model.NetworkRequest
import mezzari.torres.lucas.easy_debugger.model.NetworkResponse
import mezzari.torres.lucas.network.source.Network
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okio.Buffer
import java.lang.Exception
import java.nio.charset.Charset
import kotlin.text.Charsets.UTF_8

/**
 * @author Lucas T. Mezzari
 * @since 19/02/2020
 */
class NetworkLoggerModule: Network.OkHttpClientLevelModule {

    private val interceptor = Interceptor {
        val request = it.request()
        val url = request.url.toString()

        val networkRequest = buildRequestLogs(request)

        val response: Response
        try {
            response = it.proceed(request)

            val networkResponse = buildResponseLogs(response)

            networkLogs += NetworkLog(url, networkRequest, networkResponse, response.isSuccessful)
        } catch (e: Exception) {
            networkLogs += NetworkLog(url, networkRequest, null, false, e)
            throw e
        }

        return@Interceptor response
    }

    override fun onClientBuilderCreated(okHttpClientBuilder: okhttp3.OkHttpClient.Builder) {
        okHttpClientBuilder.addInterceptor(interceptor)
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
        val networkLogs: ArrayList<NetworkLog> = arrayListOf()
    }
}
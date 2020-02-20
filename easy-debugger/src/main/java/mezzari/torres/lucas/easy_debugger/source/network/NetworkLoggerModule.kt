package mezzari.torres.lucas.easy_debugger.source.network

import mezzari.torres.lucas.easy_debugger.model.NetworkLog
import mezzari.torres.lucas.easy_debugger.model.NetworkRequest
import mezzari.torres.lucas.easy_debugger.model.NetworkResponse
import mezzari.torres.lucas.network.source.Network
import okhttp3.Interceptor

/**
 * @author Lucas T. Mezzari
 * @since 19/02/2020
 */
class NetworkLoggerModule: Network.OkHttpClientLevelModule {

    private val interceptor = Interceptor {
        val request = it.request()
        val url = request.url.toString()

        val method = request.method
        val body = request.body
        val headers = request.headers
        val networkRequest = NetworkRequest(method, headers, body)

        val response = it.proceed(request)
        val code = response.code
        val responseHeaders = request.headers
        val responseBody = response.body
        val networkResponse = NetworkResponse(code, responseHeaders, responseBody)

        networkLogs += NetworkLog(url, networkRequest, networkResponse)

        return@Interceptor response
    }

    override fun onClientBuilderCreated(okHttpClientBuilder: okhttp3.OkHttpClient.Builder) {
        okHttpClientBuilder.addInterceptor(interceptor)
    }

    companion object {
        val networkLogs: ArrayList<NetworkLog> = arrayListOf()
    }
}
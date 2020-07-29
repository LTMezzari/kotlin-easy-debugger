package mezzari.torres.lucas.easy_debugger.source.network

import mezzari.torres.lucas.easy_debugger.model.NetworkLog
import mezzari.torres.lucas.easy_debugger.model.NetworkRequest
import mezzari.torres.lucas.easy_debugger.model.NetworkResponse
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author Lucas T. Mezzari
 * @since 29/07/2020
 */
class NetworkInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url().toString()

        val method = request.method()
        val body = request.body()
        val headers = request.headers()
        val networkRequest = NetworkRequest(method, headers, body)

        val response = chain.proceed(request)
        val code = response.code()
        val responseHeaders = request.headers()
        val responseBody = response.body()
        val networkResponse = NetworkResponse(code, responseHeaders, responseBody)

        networkLogs += NetworkLog(url, networkRequest, networkResponse)

        return response
    }

    companion object {
        val networkLogs: ArrayList<NetworkLog> = arrayListOf()
    }
}
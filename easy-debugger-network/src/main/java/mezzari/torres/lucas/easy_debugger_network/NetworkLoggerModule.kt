package mezzari.torres.lucas.easy_debugger_network

import mezzari.torres.lucas.easy_debugger.model.NetworkLog
import mezzari.torres.lucas.easy_debugger.model.NetworkRequest
import mezzari.torres.lucas.easy_debugger.model.NetworkResponse
import mezzari.torres.lucas.easy_debugger.source.network.NetworkInterceptor
import mezzari.torres.lucas.network.source.Network
import okhttp3.Interceptor

/**
 * @author Lucas T. Mezzari
 * @since 19/02/2020
 */
class NetworkLoggerModule: Network.OkHttpClientLevelModule {

    private val interceptor = NetworkInterceptor()

    override fun onClientBuilderCreated(okHttpClientBuilder: okhttp3.OkHttpClient.Builder) {
        okHttpClientBuilder.addInterceptor(interceptor)
    }
}
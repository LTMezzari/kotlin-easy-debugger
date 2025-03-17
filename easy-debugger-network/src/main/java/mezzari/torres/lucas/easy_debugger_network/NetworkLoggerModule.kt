package mezzari.torres.lucas.easy_debugger_network

import mezzari.torres.lucas.easy_debugger.network.interceptor.NetworkInterceptor
import mezzari.torres.lucas.network.source.Network

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
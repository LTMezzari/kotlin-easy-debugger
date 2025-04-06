package mezzari.torres.lucas.generic

import mezzari.torres.lucas.network.source.Network
import mezzari.torres.lucas.persistence.SessionManager
import kotlin.reflect.KClass

/**
 * @author Lucas T. Mezzari
 * @since 2020-02-20
 */
abstract class BaseService {
    private var lastUpdatedUrl: String = SessionManager.baseUrl
    private var _api: Any? = null

    inline fun <reified T>getApi(): T {
        return getApi(T::class)
    }

    fun <T>getApi(mAPI: KClass<*>): T {
        if (SessionManager.baseUrl != lastUpdatedUrl) {
            lastUpdatedUrl = SessionManager.baseUrl
            _api = Network.build(mAPI, lastUpdatedUrl)
            return _api!! as T
        } else if (_api == null) {
            _api = Network.build(mAPI, lastUpdatedUrl)
        }

        return _api!! as T
    }
}
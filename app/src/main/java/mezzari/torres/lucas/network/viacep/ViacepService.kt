package mezzari.torres.lucas.network.viacep

import mezzari.torres.lucas.generic.BaseService
import mezzari.torres.lucas.network.source.promise.NetworkPromise

/**
 * @author Lucas T. Mezzari
 * @author lucas.mezzari@operacao.rcadigital.com.br
 * @since 2020-02-20
 */
class ViacepService: BaseService() {
    private val api: IViacepAPI = getApi()

    fun getCep(cep: String): NetworkPromise<Void> {
        return NetworkPromise {
            api.getCep(cep).enqueue(this)
        }
    }
}
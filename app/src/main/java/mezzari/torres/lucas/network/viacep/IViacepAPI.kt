package mezzari.torres.lucas.network.viacep

import mezzari.torres.lucas.network.annotation.Route
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author Lucas T. Mezzari
 * @author lucas.mezzari@operacao.rcadigital.com.br
 * @since 2020-02-20
 */
@Route("https://viacep.com.br/ws/")
interface IViacepAPI {
    @GET("{cep}/json/")
    fun getCep(
        @Path("cep") cep: String
    ): Call<Void>
}
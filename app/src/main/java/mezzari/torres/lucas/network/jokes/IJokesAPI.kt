package mezzari.torres.lucas.network.jokes

import mezzari.torres.lucas.model.Joke
import mezzari.torres.lucas.network.annotation.Route
import retrofit2.Call
import retrofit2.http.*

/**
 * @author Lucas T. Mezzari
 * @author lucas.mezzari@operacao.rcadigital.com.br
 * @since 2020-02-20
 */
@Route("https://official-joke-api.appspot.com/")
interface IJokesAPI {
    @GET("jokes/random")
    fun getRandomJoke(): Call<Joke>

    @GET("jokes/{type}/random")
    fun getRandomJokeByType(
        @Path("type") type: String
    ): Call<List<Joke>>
}
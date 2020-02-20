package mezzari.torres.lucas.network.jokes

import mezzari.torres.lucas.generic.BaseService
import mezzari.torres.lucas.model.Joke
import mezzari.torres.lucas.network.source.promise.NetworkPromise

/**
 * @author Lucas T. Mezzari
 * @author lucas.mezzari@operacao.rcadigital.com.br
 * @since 2020-02-20
 */
class JokesService: BaseService() {
    private val api: IJokesAPI get() = getApi()

    fun getRandomJoke(): NetworkPromise<Joke> {
        return NetworkPromise {
            api.getRandomJoke().enqueue(this)
        }
    }

    fun getRandomJokeByType(type: String): NetworkPromise<List<Joke>> {
        return NetworkPromise {
            api.getRandomJokeByType(type).enqueue(this)
        }
    }
}
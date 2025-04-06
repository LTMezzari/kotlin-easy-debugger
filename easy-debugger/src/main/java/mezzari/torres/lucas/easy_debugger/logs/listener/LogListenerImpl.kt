package mezzari.torres.lucas.easy_debugger.logs.listener

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import mezzari.torres.lucas.core.interfaces.AppDispatcher

/**
 * @author Lucas T. Mezzari
 * @since 15/03/25
 **/
internal class LogListenerImpl(private val dispatcher: AppDispatcher) : LogListener {

    private var job: Job? = null
    private var mFlow: Flow<String>? = null
    private var logs: ArrayList<String> = ArrayList()

    override fun getLogs(): Flow<String>? = mFlow

    override fun startListening() {
        val scope = CoroutineScope(Job() + dispatcher.io)
        job = scope.launch {
            mFlow = flow {
                emit(logs.joinToString())
                while (true) {
                    Runtime.getRuntime().exec("logcat -c")
                    Runtime.getRuntime().exec("logcat").inputStream.bufferedReader()
                        .useLines { lines ->
                            lines.forEach {
                                emit(it)
                                logs.add(it)
                            }
                        }
                }
            }
                .flowOn(Dispatchers.IO)
        }
    }

    override suspend fun stopListening() {
        job?.cancelAndJoin()
        job = null
        mFlow = null
    }
}
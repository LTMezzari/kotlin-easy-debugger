package mezzari.torres.lucas.easy_debugger.logs.listener

import kotlinx.coroutines.flow.Flow

/**
 * @author Lucas T. Mezzari
 * @since 15/03/25
 **/
interface LogListener {

    fun getLogs(): Flow<String>?

    fun startListening()

    suspend fun stopListening()
}
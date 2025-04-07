package mezzari.torres.lucas.easy_debugger.interfaces

import kotlinx.coroutines.CoroutineDispatcher

/**
 * @author Lucas T. Mezzari
 * @since 15/03/25
 **/
interface AppDispatcher {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
}
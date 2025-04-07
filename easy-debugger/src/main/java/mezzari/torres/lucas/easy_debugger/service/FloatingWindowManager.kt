package mezzari.torres.lucas.easy_debugger.service

import android.content.Context
import android.content.Intent
import mezzari.torres.lucas.easy_debugger.generic.BaseFloatingViewService
import kotlin.reflect.KClass

/**
 * @author Lucas T. Mezzari
 * @since 25/03/25
 **/
interface FloatingWindowManager {
    fun <T : mezzari.torres.lucas.easy_debugger.generic.BaseFloatingViewService> createFloatingWindow(
        context: Context,
        service: KClass<T>,
        intent: Intent? = null
    )

    fun <T : mezzari.torres.lucas.easy_debugger.generic.BaseFloatingViewService> destroyFloatingWindow(
        context: Context,
        service: KClass<T>,
        intent: Intent? = null
    )
}
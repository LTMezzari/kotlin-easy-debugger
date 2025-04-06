package mezzari.torres.lucas.core.service

import android.content.Context
import android.content.Intent
import mezzari.torres.lucas.core.generic.BaseFloatingViewService
import kotlin.reflect.KClass

/**
 * @author Lucas T. Mezzari
 * @since 25/03/25
 **/
interface FloatingWindowManager {
    fun <T : BaseFloatingViewService> createFloatingWindow(
        context: Context,
        service: KClass<T>,
        intent: Intent? = null
    )

    fun <T : BaseFloatingViewService> destroyFloatingWindow(
        context: Context,
        service: KClass<T>,
        intent: Intent? = null
    )
}
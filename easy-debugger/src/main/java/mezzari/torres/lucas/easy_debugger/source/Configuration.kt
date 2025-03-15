package mezzari.torres.lucas.easy_debugger.source

import android.app.Application
import android.content.Intent
import android.view.View
import mezzari.torres.lucas.easy_debugger.debug.model.DebugOption
import mezzari.torres.lucas.easy_debugger.exception.handler.ExceptionHandler
import mezzari.torres.lucas.easy_debugger.service.FloatingDebugViewService
import mezzari.torres.lucas.easy_debugger.exception.handler.RedirectExceptionHandler
import mezzari.torres.lucas.easy_debugger.exception.view.ExceptionActivity
import mezzari.torres.lucas.easy_debugger.network.view.NetworkLoggerActivity
import kotlin.reflect.KClass

/**
 * @author Lucas T. Mezzari
 * @since 19/02/2020
 */
class Configuration private constructor() {

    internal var shouldUseDefaultHandler: Boolean = false
        private set

    internal var activityListener: Application.ActivityLifecycleCallbacks? = null
        private set

    internal var exceptionHandler: ExceptionHandler =
        RedirectExceptionHandler(ExceptionActivity::class)
        private set

    internal var hasFloatingView: Boolean = true
        private set

    internal var floatingViewService: KClass<*> = FloatingDebugViewService::class
        private set

    internal var onFloatingViewClickListener: ((View) -> Unit)? = null
        private set

    internal var debugOptions: List<DebugOption> = listOf(
        DebugOption("Network Logs") { context ->
            context.startActivity(Intent(context, NetworkLoggerActivity::class.java))
        },
    )
        private set

    class Builder {
        private val configuration: Configuration = Configuration()

        fun setEnable(isEnable: Boolean): Builder {
            configuration.shouldUseDefaultHandler = isEnable
            return this
        }

        fun setFloatingViewEnable(isEnable: Boolean): Builder {
            configuration.hasFloatingView = isEnable
            return this
        }

        fun setActivityListener(activityListener: Application.ActivityLifecycleCallbacks): Builder {
            configuration.activityListener = activityListener
            return this
        }

        fun setExceptionHandler(exceptionHandler: ExceptionHandler): Builder {
            configuration.exceptionHandler = exceptionHandler
            return this
        }

        fun setFloatingViewService(service: KClass<*>): Builder {
            configuration.floatingViewService = service
            return this
        }

        fun setOnFloatingViewClickListener(onFloatingViewClockListener: (View) -> Unit): Builder {
            configuration.onFloatingViewClickListener = onFloatingViewClockListener
            return this
        }

        fun setDebugOptions(debugOptions: List<DebugOption>): Builder {
            configuration.debugOptions = debugOptions
            return this
        }

        fun build() {
            EasyDebugger.initialize(configuration)
        }
    }
}
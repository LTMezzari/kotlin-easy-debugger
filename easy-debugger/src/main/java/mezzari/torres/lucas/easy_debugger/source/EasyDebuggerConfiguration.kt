package mezzari.torres.lucas.easy_debugger.source

import android.app.Application
import mezzari.torres.lucas.easy_debugger.BuildConfig
import mezzari.torres.lucas.easy_debugger.generic.ExceptionHandler
import mezzari.torres.lucas.easy_debugger.source.exception.RedirectExceptionHandler
import mezzari.torres.lucas.easy_debugger.view.ExceptionActivity

/**
 * @author Lucas T. Mezzari
 * @since 19/02/2020
 */
class EasyDebuggerConfiguration {
    var shouldUseDefaultHandler: Boolean = !BuildConfig.DEBUG
        private set

    var activityListener: Application.ActivityLifecycleCallbacks? = null
        private set

    var exceptionHandler: ExceptionHandler = RedirectExceptionHandler(ExceptionActivity::class)
        private set

    var hasFloatingView: Boolean = false
        private set

    class Builder {
        private val configuration: EasyDebuggerConfiguration = EasyDebuggerConfiguration()

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

        fun build(): EasyDebuggerConfiguration {
            return configuration
        }
    }
}
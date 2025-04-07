package mezzari.torres.lucas.easy_debugger.generic

import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager

/**
 * @author Lucas T. Mezzari
 * @since 2020-02-20
 */
abstract class BaseFloatingViewService : BaseService() {

    protected lateinit var floatingView: View
    protected val windowManager: WindowManager by lazy {
        getSystemService(WINDOW_SERVICE) as WindowManager
    }
    protected lateinit var params: WindowManager.LayoutParams

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()

        //Execute the onCreate in the next frame so that the child services can prepare itself
        Handler(mainLooper).post {
            val inflater = LayoutInflater.from(this)
            floatingView = onCreateView(inflater, null)
            params = onCreateLayoutParams()
            windowManager.addView(floatingView, params)
            onViewCreated(floatingView)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::floatingView.isInitialized)
            windowManager.removeView(floatingView)
    }

    abstract fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?): View

    open fun onViewCreated(view: View) {}

    open fun onCreateLayoutParams(): WindowManager.LayoutParams {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
            )
        } else {
            WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
            )
        }
    }

    open fun invalidateLayout() {
        windowManager.updateViewLayout(floatingView, params)
    }
}
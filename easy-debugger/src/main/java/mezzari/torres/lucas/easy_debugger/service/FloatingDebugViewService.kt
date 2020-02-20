package mezzari.torres.lucas.easy_debugger.service

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import kotlinx.android.synthetic.main.layout_floating_debug_view.view.*
import mezzari.torres.lucas.easy_debugger.R
import mezzari.torres.lucas.easy_debugger.source.EasyDebugger

/**
 * @author Lucas T. Mezzari
 * @author lucas.mezzari@operacao.rcadigital.com.br
 * @since 2020-02-20
 */
class FloatingDebugViewService: Service() {

    private lateinit var floatingDebugView: View
    private val windowManager: WindowManager by lazy {
        getSystemService(WINDOW_SERVICE) as WindowManager
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()

        val inflater = LayoutInflater.from(this)
        floatingDebugView = inflater.inflate(R.layout.layout_floating_debug_view, null)

        val params = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT)
        } else {
            WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT)
        }
        params.x = 0
        params.y = 0

        windowManager.addView(floatingDebugView, params)

        floatingDebugView.rlDebugContainer.setOnClickListener {
        }

        floatingDebugView.rlDebugContainer.setOnTouchListener(object: View.OnTouchListener {

            private var initialX: Int = 0
            private var initialY: Int = 0
            private var initialTouchX: Float = 0F
            private var initialTouchY: Float = 0F

            private var hasMoved = false

            override fun onTouch(view: View?, event: MotionEvent?): Boolean {
                when (event?.actionMasked) {
                    MotionEvent.ACTION_DOWN -> {
                        initialX = params.x
                        initialY = params.y
                        initialTouchX = event.rawX
                        initialTouchY = event.rawY
                        return true
                    }
                    MotionEvent.ACTION_UP -> {
                        if (hasMoved)
                            view?.performClick()
                        hasMoved = false
                        return true
                    }
                    MotionEvent.ACTION_MOVE -> {
                        hasMoved = true
                        params.x = initialX + ((event.rawX - initialTouchX)).toInt()
                        params.y = initialY + ((event.rawY - initialTouchY)).toInt()
                        windowManager.updateViewLayout(floatingDebugView, params)
                        return true
                    }
                }
                return false
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::floatingDebugView.isInitialized)
            windowManager.removeView(floatingDebugView)
    }
}
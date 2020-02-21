package mezzari.torres.lucas.easy_debugger.service

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.layout_floating_debug_view.view.*
import mezzari.torres.lucas.easy_debugger.R
import mezzari.torres.lucas.easy_debugger.generic.BaseFloatingViewService
import mezzari.torres.lucas.easy_debugger.source.EasyDebugger


/**
 * @author Lucas T. Mezzari
 * @author lucas.mezzari@operacao.rcadigital.com.br
 * @since 2020-02-20
 */
class FloatingDebugViewService: BaseFloatingViewService() {

    private lateinit var floatingDebugView: View

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?): View {
        return inflater.inflate(R.layout.layout_floating_debug_view, parent)
    }

    override fun onViewCreated(view: View) {
        super.onViewCreated(view)
        floatingDebugView = view

        floatingDebugView.rlDebugContainer.setOnClickListener {
            EasyDebugger.configuration.onFloatingViewClickListener?.invoke(it)
        }

        floatingDebugView.rlDebugContainer.setOnTouchListener(object: View.OnTouchListener {

            private var initialX: Int = 0
            private var initialY: Int = 0
            private var initialTouchX: Float = 0F
            private var initialTouchY: Float = 0F

            override fun onTouch(view: View?, e: MotionEvent?): Boolean {
                val event = e ?: return false
                val duration = event.eventTime - event.downTime
                when (event.actionMasked) {
                    MotionEvent.ACTION_DOWN -> {
                        initialX = params.x
                        initialY = params.y
                        initialTouchX = event.rawX
                        initialTouchY = event.rawY
                        return true
                    }

                    MotionEvent.ACTION_UP -> {
                        if (duration < 100)
                            view?.performClick()

                        return true
                    }

                    MotionEvent.ACTION_MOVE -> {
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
}
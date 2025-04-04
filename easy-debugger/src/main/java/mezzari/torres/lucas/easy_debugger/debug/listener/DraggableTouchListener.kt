package mezzari.torres.lucas.easy_debugger.debug.listener

import android.content.Context
import android.graphics.Point
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import java.util.Timer
import kotlin.concurrent.timerTask
import kotlin.math.hypot

/**
 * @author Lucas T. Mezzari
 * @since 03/04/25
 **/
internal class DraggableTouchListener(
    context: Context,
    private val view: View,
    private val initialPosition: () -> Point,
    private val listener: ((x: Int, y: Int) -> Unit)? = null
) : View.OnTouchListener {

    private val touchSlop = ViewConfiguration.get(context).scaledTouchSlop
    private val longClickInterval = ViewConfiguration.getLongPressTimeout()
    private var pointerStartX = 0
    private var pointerStartY = 0
    private var initialX = 0
    private var initialY = 0
    private var isMoving = false
    private var longClickWasPerformed = false
    private var timer: Timer? = null

    init {
        view.setOnTouchListener(this)
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                pointerStartX = event.rawX.toInt()
                pointerStartY = event.rawY.toInt()
                with(initialPosition()) {
                    initialX = x
                    initialY = y
                }
                isMoving = false
                longClickWasPerformed = false
                scheduleLongClickTimer()
            }

            MotionEvent.ACTION_MOVE -> {
                if (longClickWasPerformed) {
                    return true
                }
                val deltaX = event.rawX - pointerStartX
                val deltaY = event.rawY - pointerStartY
                if (isMoving || hypot(deltaX, deltaY) > touchSlop) {
                    cancelLongClickTimer()
                    listener?.invoke(initialX + deltaX.toInt(), initialY + deltaY.toInt())
                    isMoving = true
                }
            }

            MotionEvent.ACTION_UP -> {
                cancelLongClickTimer()
                if (!isMoving && !longClickWasPerformed) {
                    view.performClick()
                }
            }
        }
        return true
    }

    private fun scheduleLongClickTimer() {
        if (timer != null) {
            return
        }
        timer = Timer()
        timer?.schedule(timerTask {
            if (isMoving || longClickWasPerformed) {
                cancelLongClickTimer()
                return@timerTask
            }
            view.post { view.performLongClick() }
            longClickWasPerformed = true
            cancelLongClickTimer()
        }, longClickInterval.toLong())
    }

    private fun cancelLongClickTimer() {
        timer?.cancel()
        timer = null
    }
}
package mezzari.torres.lucas.easy_debugger.print

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import androidx.core.graphics.createBitmap
import androidx.fragment.app.Fragment

/**
 * @author Lucas T. Mezzari
 * @since 29/03/25
 **/
class PrintScreenManagerImpl: PrintScreenManager {
    override fun printActivity(activity: Activity): Bitmap {
        val rootView = activity.window.decorView.rootView
        return printView(rootView)
    }

    override fun printFragment(fragment: Fragment): Bitmap {
        val view = fragment.view ?: throw IllegalArgumentException("$fragment does not have content to be printed")
        return printView(view)
    }

    override fun printView(view: View): Bitmap {
        val print = createBitmap(view.width, view.height)
        val canvas = Canvas(print)
        view.draw(canvas)
        return print
    }
}
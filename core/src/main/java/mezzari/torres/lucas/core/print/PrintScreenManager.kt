package mezzari.torres.lucas.core.print

import android.app.Activity
import android.graphics.Bitmap
import android.view.View
import androidx.fragment.app.Fragment

/**
 * @author Lucas T. Mezzari
 * @since 29/03/25
 **/
interface PrintScreenManager {
    fun printActivity(activity: Activity): Bitmap

    fun printFragment(fragment: Fragment): Bitmap

    fun printView(view: View): Bitmap
}
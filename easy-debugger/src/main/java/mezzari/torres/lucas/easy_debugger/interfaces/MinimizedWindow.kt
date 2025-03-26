package mezzari.torres.lucas.easy_debugger.interfaces

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager

/**
 * @author Lucas T. Mezzari
 * @since 22/03/25
 **/
interface MinimizedWindow {
    fun onCreate(context: Context) {}

    fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?): View?

    fun onViewCreated(view: View) {}

    fun onDestroy() {}

    fun onCreateLayoutParams(params: WindowManager.LayoutParams): WindowManager.LayoutParams =
        params
}
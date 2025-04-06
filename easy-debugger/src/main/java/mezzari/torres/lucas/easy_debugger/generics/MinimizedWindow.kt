package mezzari.torres.lucas.easy_debugger.generics

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager

/**
 * @author Lucas T. Mezzari
 * @since 22/03/25
 **/
abstract class MinimizedWindow {

    protected lateinit var context: Context

    open fun onCreate(context: Context) {
        this.context = context
    }

    abstract fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?): View?

    open fun onViewCreated(view: View) {}

    open fun onDestroy() {}

    open fun onCreateLayoutParams(params: WindowManager.LayoutParams): WindowManager.LayoutParams =
        params
}
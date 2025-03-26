package mezzari.torres.lucas.easy_debugger.debug.model

import android.content.Context

/**
 * @author Lucas T. Mezzari
 * @since 22/03/25
 **/
interface DebugDialogCallback {
    fun onOptionClick(context: Context)
}

internal class SimpleDebugDialogCallback(private val onClick: (Context) -> Unit) :
    DebugDialogCallback {
    override fun onOptionClick(context: Context) {
        onClick.invoke(context)
    }
}
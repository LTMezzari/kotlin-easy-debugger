package mezzari.torres.lucas.easy_debugger.debug.model

import android.content.Context

/**
 * @author Lucas T. Mezzari
 * @since 14/03/25
 **/
data class DebugOption(
    val name: String,
    val onClick: DebugDialogCallback,
) {
    constructor(
        name: String,
        onClick: (Context) -> Unit
    ) : this(name, SimpleDebugDialogCallback(onClick))
}
package mezzari.torres.lucas.easy_debugger.debug.dialog.model

import android.content.Context

/**
 * @author Lucas T. Mezzari
 * @since 14/03/25
 **/
data class DebugOption(
    val name: String,
    val onClick: (Context) -> Unit,
)
package mezzari.torres.lucas.easy_debugger.archive

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import mezzari.torres.lucas.easy_debugger.source.model.ObservableList

/**
 * @author Lucas T. Mezzari
 * @since 26/03/25
 **/
fun <T> listenWithViewLifecycleOwner(
    owner: LifecycleOwner,
    list: ObservableList<T>,
    listener: ObservableList.Listener<T>
) {
    owner.lifecycle.addObserver(ObservableListLifecycleObserver(list, listener))
}

fun <T, VH : RecyclerView.ViewHolder> listenWithAdapter(
    owner: LifecycleOwner,
    list: ObservableList<T>,
    adapter: RecyclerView.Adapter<VH>,
    listener: ObservableList.Listener<T>? = null
) {
    owner.lifecycle.addObserver(
        ObservableListLifecycleObserver(
            list,
            adapter.listenerForObservableList(listener)
        )
    )
}

private class ObservableListLifecycleObserver<T>(
    private val list: ObservableList<T>,
    private val listener: ObservableList.Listener<T>
) : DefaultLifecycleObserver {
    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        list.addListener(listener)
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        list.removeListener(listener)
    }
}
package mezzari.torres.lucas.easy_debugger.archive

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import mezzari.torres.lucas.easy_debugger.source.model.ObservableList

/**
 * @author Lucas T. Mezzari
 * @since 26/03/25
 **/
fun <T> RecyclerView.Adapter<*>.listenerForObservableList(
    listener: ObservableList.Listener<T>? = null
): ObservableList.Listener<T> {
    return object : ObservableList.Listener<T> {
        override fun onItemAdded(list: ObservableList<T>, index: Int, item: T) {
            super.onItemAdded(list, index, item)
            notifyItemInserted(index)
            listener?.onItemAdded(list, index, item)
        }

        override fun onItemRemoved(list: ObservableList<T>, index: Int, item: T) {
            super.onItemRemoved(list, index, item)
            notifyItemRemoved(index)
            listener?.onItemRemoved(list, index, item)
        }

        override fun onItemChanged(
            list: ObservableList<T>,
            index: Int,
            currentItem: T,
            previousItem: T
        ) {
            super.onItemChanged(list, index, currentItem, previousItem)
            notifyItemChanged(index)
            listener?.onItemChanged(list, index, currentItem, previousItem)
        }

        override fun onRangeAdded(
            list: ObservableList<T>,
            items: Collection<T>,
            positionStart: Int,
            itemCount: Int
        ) {
            super.onRangeAdded(list, items, positionStart, itemCount)
            notifyItemRangeInserted(positionStart, itemCount)
            listener?.onRangeAdded(list, items, positionStart, itemCount)
        }

        override fun onRangeRemoved(
            list: ObservableList<T>,
            items: Collection<T>,
            positionStart: Int,
            itemCount: Int
        ) {
            super.onRangeRemoved(list, items, positionStart, itemCount)
            notifyItemRangeRemoved(positionStart, itemCount)
            listener?.onRangeRemoved(list, items, positionStart, itemCount)
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun onListChanged(list: ObservableList<T>) {
            super.onListChanged(list)
            notifyDataSetChanged()
            listener?.onListChanged(list)
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun onListRemoved(list: ObservableList<T>, items: Collection<T>) {
            super.onListRemoved(list, items)
            notifyDataSetChanged()
            listener?.onListRemoved(list, items)
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun onListCleared() {
            super.onListCleared()
            notifyDataSetChanged()
            listener?.onListCleared()
        }
    }
}
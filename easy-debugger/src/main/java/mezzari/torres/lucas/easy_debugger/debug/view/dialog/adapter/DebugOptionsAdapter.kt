package mezzari.torres.lucas.easy_debugger.debug.view.dialog.adapter

import android.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mezzari.torres.lucas.easy_debugger.debug.model.DebugOption

/**
 * @author Lucas T. Mezzari
 * @since 14/03/25
 **/
internal class DebugOptionsAdapter(
    context: Context,
    private val items: List<DebugOption>,
    private val onCLickListener: ((DebugOption) -> Unit)? = null,
) :
    RecyclerView.Adapter<DebugOptionsAdapter.DebugOptionsViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DebugOptionsViewHolder {
        return DebugOptionsViewHolder(
            inflater.inflate(
                R.layout.simple_list_item_1,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: DebugOptionsViewHolder, position: Int) {
        val textView = holder.itemView as? TextView ?: return
        val item = items[position]
        textView.text = item.name
        textView.setOnClickListener {
            onCLickListener?.invoke(item)
        }
    }

    class DebugOptionsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
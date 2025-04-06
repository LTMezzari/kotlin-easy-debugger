package mezzari.torres.lucas.easy_debugger.logs.view.adapter

import android.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Lucas T. Mezzari
 * @since 15/03/25
 **/
internal class LogAdapter(context: Context) : RecyclerView.Adapter<LogAdapter.LogViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    private var logs: ArrayList<String> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogViewHolder {
        return LogViewHolder(inflater.inflate(R.layout.simple_list_item_1, parent, false))
    }

    override fun onBindViewHolder(holder: LogViewHolder, position: Int) {
        val textView = holder.itemView as? TextView ?: return
        val item = logs[position]
        textView.text = item
    }

    override fun getItemCount(): Int {
        return logs.size
    }

    fun addLog(log: String) {
        logs.add(log)
        notifyItemInserted(logs.size)
    }

    class LogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
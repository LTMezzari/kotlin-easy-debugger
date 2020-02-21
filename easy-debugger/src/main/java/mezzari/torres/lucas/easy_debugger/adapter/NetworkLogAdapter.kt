package mezzari.torres.lucas.easy_debugger.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mezzari.torres.lucas.easy_debugger.model.NetworkLog
import mezzari.torres.lucas.easy_debugger.source.network.NetworkLoggerModule

/**
 * @author Lucas T. Mezzari
 * @author lucas.mezzari@operacao.rcadigital.com.br
 * @since 2020-02-20
 */
internal class NetworkLogAdapter(context: Context): RecyclerView.Adapter<NetworkLogAdapter.NetworkLogViewHolder>() {

    private val networkLogs: List<NetworkLog> = NetworkLoggerModule.networkLogs
    private var items: List<Item> = extractItems()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NetworkLogViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        return if (items.isEmpty()) {
            1
        } else {
            items.size
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (items.isEmpty())
            return VIEW_TYPE_EMPTY

        val item = items[position]
        return when {
            item is HeaderItem && !item.isExpanded -> {
                VIEW_TYPE_CLOSED
            }

            item is HeaderItem -> {
                VIEW_TYPE_HEADER
            }

            item is FooterItem -> {
                VIEW_TYPE_FOOTER
            }

            else -> {
                VIEW_TYPE_CONTENT
            }
        }
    }

    override fun onBindViewHolder(holder: NetworkLogViewHolder, position: Int) {

    }

    private fun extractItems(): List<Item> {
        return arrayListOf()
    }

    internal class NetworkLogViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    internal abstract class Item

    internal class HeaderItem(
        val networkLog: NetworkLog,
        val isExpanded: Boolean
    ): Item()

    internal class ContentItem(
        val networkLog: NetworkLog
    ): Item()

    internal class FooterItem(
        val networkLog: NetworkLog
    ): Item()

    companion object {
        const val VIEW_TYPE_EMPTY = 0
        const val VIEW_TYPE_CLOSED = 1
        const val VIEW_TYPE_HEADER = 2
        const val VIEW_TYPE_FOOTER = 3
        const val VIEW_TYPE_CONTENT = 4
    }
}
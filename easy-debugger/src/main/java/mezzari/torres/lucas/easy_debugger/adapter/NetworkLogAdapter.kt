package mezzari.torres.lucas.easy_debugger.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_log_collapsed.view.*
import kotlinx.android.synthetic.main.row_log_content.view.*
import mezzari.torres.lucas.easy_debugger.R
import mezzari.torres.lucas.easy_debugger.model.NetworkLog
import mezzari.torres.lucas.easy_debugger.source.network.NetworkInterceptor

/**
 * @author Lucas T. Mezzari
 * @author lucas.mezzari@operacao.rcadigital.com.br
 * @since 2020-02-20
 */
internal class NetworkLogAdapter(context: Context): RecyclerView.Adapter<NetworkLogAdapter.NetworkLogViewHolder>() {

    private val inflater = LayoutInflater.from(context)

    private val networkLogs: List<NetworkLog> = NetworkInterceptor.networkLogs
    private var items: ArrayList<Item> = extractItems()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NetworkLogViewHolder {
        return NetworkLogViewHolder(inflater.inflate(when (viewType) {
            VIEW_TYPE_EMPTY -> {
                R.layout.row_empty_logs
            }
            VIEW_TYPE_HEADER -> {
                R.layout.row_log_header
            }
            VIEW_TYPE_CONTENT -> {
                R.layout.row_log_content
            }
            VIEW_TYPE_FOOTER -> {
                R.layout.row_log_footer
            }
            else -> {
                R.layout.row_log_collapsed
            }
        }, parent, false))
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
                VIEW_TYPE_COLLAPSED
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
        when (getItemViewType(position)) {
            VIEW_TYPE_COLLAPSED -> {
                val headerItem = items[position] as HeaderItem
                val log = headerItem.networkLog

                holder.itemView.tvUrl.text = log.url
                holder.itemView.tvMethod.text = log.request.method
                holder.itemView.tvCode.text = log.response?.code.toString()

                if (log.wasSuccessful) {
                    holder.itemView.tvUrl.background.setTintList(ColorStateList.valueOf(Color.GREEN))
                } else {
                    holder.itemView.tvUrl.background.setTintList(ColorStateList.valueOf(Color.RED))
                }

                holder.itemView.setOnClickListener {
                    headerItem.isExpanded = !headerItem.isExpanded
                    handleExpansion(headerItem)
                }
            }

            VIEW_TYPE_HEADER -> {
                val headerItem = items[position] as HeaderItem
                val log = headerItem.networkLog

                holder.itemView.tvUrl.text = log.url

                if (log.wasSuccessful) {
                    holder.itemView.tvUrl.background.setTintList(ColorStateList.valueOf(Color.GREEN))
                } else {
                    holder.itemView.tvUrl.background.setTintList(ColorStateList.valueOf(Color.RED))
                }

                holder.itemView.setOnClickListener {
                    headerItem.isExpanded = !headerItem.isExpanded
                    handleExpansion(headerItem)
                }
            }

            VIEW_TYPE_CONTENT -> {
                val contentItem = items[position] as ContentItem
                val log = contentItem.networkLog

                holder.itemView.tvRequest.text = log.stringifyRequest() ?: "There was no Request"

                if (!log.wasSuccessful && log.response == null) {
                    holder.itemView.tvResponse.text = log.error?.message ?: "Unknown Error"
                } else {
                    holder.itemView.tvResponse.text = log.stringifyResponse() ?: "There was no Response"
                }
            }

            VIEW_TYPE_FOOTER -> {
                val footerItem = items[position] as FooterItem
                val log = footerItem.networkLog

                holder.itemView.tvMethod.text = log.request.method
                holder.itemView.tvCode.text = log.response?.code?.toString() ?: "Unknown Error"
            }
        }
    }

    private fun extractItems(): ArrayList<Item> {
        val items = arrayListOf<Item>()

        for (log in networkLogs) {
            items += HeaderItem(log, ContentItem(log), FooterItem(log))
        }

        return items
    }

    private fun handleExpansion(headerItem: HeaderItem) {
        val position = items.indexOf(headerItem)
        if (position == -1)
            return

        if (headerItem.isExpanded) {
            items.add(position + 1, headerItem.content)
            items.add(position + 2, headerItem.footer)
            notifyItemChanged(position)
            notifyItemRangeInserted(position + 1, 2)
        } else {
            items.remove(headerItem.content)
            items.remove(headerItem.footer)
            notifyItemChanged(position)
            notifyItemRangeRemoved(position + 1, 2)
        }
    }

    internal class NetworkLogViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    internal abstract class Item

    internal class HeaderItem(
        val networkLog: NetworkLog,
        val content: ContentItem,
        val footer: FooterItem,
        var isExpanded: Boolean = false
    ): Item()

    internal class ContentItem(
        val networkLog: NetworkLog
    ): Item()

    internal class FooterItem(
        val networkLog: NetworkLog
    ): Item()

    companion object {
        const val VIEW_TYPE_EMPTY = 0
        const val VIEW_TYPE_COLLAPSED = 1
        const val VIEW_TYPE_HEADER = 2
        const val VIEW_TYPE_FOOTER = 3
        const val VIEW_TYPE_CONTENT = 4
    }
}
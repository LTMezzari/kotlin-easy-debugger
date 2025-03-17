package mezzari.torres.lucas.easy_debugger.network.view.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mezzari.torres.lucas.easy_debugger.databinding.RowEmptyLogsBinding
import mezzari.torres.lucas.easy_debugger.databinding.RowLogCollapsedBinding
import mezzari.torres.lucas.easy_debugger.databinding.RowLogContentBinding
import mezzari.torres.lucas.easy_debugger.databinding.RowLogFooterBinding
import mezzari.torres.lucas.easy_debugger.databinding.RowLogHeaderBinding
import mezzari.torres.lucas.easy_debugger.network.model.NetworkLog
import mezzari.torres.lucas.easy_debugger.network.interceptor.NetworkInterceptor

/**
 * @author Lucas T. Mezzari
 * @author lucas.mezzari@operacao.rcadigital.com.br
 * @since 2020-02-20
 */
internal class NetworkLogAdapter(context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val inflater = LayoutInflater.from(context)

    private val networkLogs: List<NetworkLog> = NetworkInterceptor.networkLogs
    private var items: ArrayList<Item> = extractItems()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_EMPTY -> {
                NetworkEmptyLogViewHolder(
                    RowEmptyLogsBinding.inflate(
                        inflater,
                        parent,
                        false
                    )
                )
            }

            VIEW_TYPE_HEADER -> {
                NetworkLogHeaderViewHolder(
                    RowLogHeaderBinding.inflate(
                        inflater,
                        parent,
                        false
                    )
                )
            }

            VIEW_TYPE_CONTENT -> {
                NetworkLogContentViewHolder(
                    RowLogContentBinding.inflate(
                        inflater,
                        parent,
                        false
                    )
                )
            }

            VIEW_TYPE_FOOTER -> {
                NetworkLogFooterViewHolder(
                    RowLogFooterBinding.inflate(
                        inflater,
                        parent,
                        false
                    )
                )
            }

            else -> {
                NetworkLogCollapsedViewHolder(
                    RowLogCollapsedBinding.inflate(
                        inflater,
                        parent,
                        false
                    )
                )
            }
        }
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

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            VIEW_TYPE_COLLAPSED -> {
                val holder = viewHolder as NetworkLogCollapsedViewHolder
                val headerItem = items[position] as HeaderItem
                val log = headerItem.networkLog

                holder.binding.tvUrl.text = log.url
                holder.binding.tvMethod.text = log.request.method
                holder.binding.tvCode.text = log.response?.code.toString()

                if (log.wasSuccessful) {
                    holder.binding.tvUrl.background.setTintList(ColorStateList.valueOf(Color.GREEN))
                } else {
                    holder.binding.tvUrl.background.setTintList(ColorStateList.valueOf(Color.RED))
                }

                holder.itemView.setOnClickListener {
                    headerItem.isExpanded = !headerItem.isExpanded
                    handleExpansion(headerItem)
                }
            }

            VIEW_TYPE_HEADER -> {
                val holder = viewHolder as NetworkLogHeaderViewHolder
                val headerItem = items[position] as HeaderItem
                val log = headerItem.networkLog

                holder.binding.tvUrl.text = log.url

                if (log.wasSuccessful) {
                    holder.binding.tvUrl.background.setTintList(ColorStateList.valueOf(Color.GREEN))
                } else {
                    holder.binding.tvUrl.background.setTintList(ColorStateList.valueOf(Color.RED))
                }

                holder.itemView.setOnClickListener {
                    headerItem.isExpanded = !headerItem.isExpanded
                    handleExpansion(headerItem)
                }
            }

            VIEW_TYPE_CONTENT -> {
                val holder = viewHolder as NetworkLogContentViewHolder
                val contentItem = items[position] as ContentItem
                val log = contentItem.networkLog

                holder.binding.tvRequest.text = log.stringifyRequest() ?: "There was no Request"

                if (!log.wasSuccessful && log.response == null) {
                    holder.binding.tvResponse.text = log.error?.message ?: "Unknown Error"
                } else {
                    holder.binding.tvResponse.text =
                        log.stringifyResponse() ?: "There was no Response"
                }
            }

            VIEW_TYPE_FOOTER -> {
                val holder = viewHolder as NetworkLogFooterViewHolder
                val footerItem = items[position] as FooterItem
                val log = footerItem.networkLog

                holder.binding.tvMethod.text = log.request.method
                holder.binding.tvCode.text = log.response?.code?.toString() ?: "Unknown Error"
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

    internal class NetworkEmptyLogViewHolder(val binding: RowEmptyLogsBinding) :
        RecyclerView.ViewHolder(binding.root)

    internal class NetworkLogHeaderViewHolder(val binding: RowLogHeaderBinding) :
        RecyclerView.ViewHolder(binding.root)

    internal class NetworkLogContentViewHolder(val binding: RowLogContentBinding) :
        RecyclerView.ViewHolder(binding.root)

    internal class NetworkLogFooterViewHolder(val binding: RowLogFooterBinding) :
        RecyclerView.ViewHolder(binding.root)

    internal class NetworkLogCollapsedViewHolder(val binding: RowLogCollapsedBinding) :
        RecyclerView.ViewHolder(binding.root)

    internal abstract class Item

    internal class HeaderItem(
        val networkLog: NetworkLog,
        val content: ContentItem,
        val footer: FooterItem,
        var isExpanded: Boolean = false
    ) : Item()

    internal class ContentItem(
        val networkLog: NetworkLog
    ) : Item()

    internal class FooterItem(
        val networkLog: NetworkLog
    ) : Item()

    companion object {
        const val VIEW_TYPE_EMPTY = 0
        const val VIEW_TYPE_COLLAPSED = 1
        const val VIEW_TYPE_HEADER = 2
        const val VIEW_TYPE_FOOTER = 3
        const val VIEW_TYPE_CONTENT = 4
    }
}
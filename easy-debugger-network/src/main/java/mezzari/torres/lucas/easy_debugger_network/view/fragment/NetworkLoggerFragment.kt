package mezzari.torres.lucas.easy_debugger_network.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import mezzari.torres.lucas.easy_debugger.archive.listenWithViewLifecycleOwner
import mezzari.torres.lucas.easy_debugger.di.appDispatcher
import mezzari.torres.lucas.easy_debugger.generic.BaseFragment
import mezzari.torres.lucas.easy_debugger.interfaces.AppDispatcher
import mezzari.torres.lucas.easy_debugger.source.model.ObservableList
import mezzari.torres.lucas.easy_debugger_network.databinding.FragmentNetworkLoggerBinding
import mezzari.torres.lucas.easy_debugger_network.interceptor.NetworkInterceptor
import mezzari.torres.lucas.easy_debugger_network.model.NetworkLog
import mezzari.torres.lucas.easy_debugger_network.view.adapter.NetworkLogAdapter

/**
 * @author Lucas T. Mezzari
 * @since 21/02/2020
 **/
internal class NetworkLoggerFragment : BaseFragment() {

    private lateinit var binding: FragmentNetworkLoggerBinding

    private val adapter: NetworkLogAdapter by lazy {
        NetworkLogAdapter(requireContext(), networkLogs)
    }

    private val networkLogs: ObservableList<NetworkLog> by lazy {
        NetworkInterceptor.networkLogs
    }

    private val dispatcher: AppDispatcher by lazy {
        appDispatcher
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentNetworkLoggerBinding.inflate(inflater, container, false).let {
            binding = it
            return@let binding.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvNetworkLogs.apply {
            adapter = this@NetworkLoggerFragment.adapter
            layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }

        listenWithViewLifecycleOwner(
            viewLifecycleOwner,
            networkLogs,
            object : ObservableList.Listener<NetworkLog> {
                override fun onItemAdded(
                    list: ObservableList<NetworkLog>,
                    index: Int,
                    item: NetworkLog
                ) {
                    super.onItemAdded(list, index, item)
                    CoroutineScope(dispatcher.main).launch {
                        adapter.addItem(item)
                    }
                }

                @SuppressLint("NotifyDataSetChanged")
                override fun onListCleared() {
                    super.onListCleared()
                    CoroutineScope(dispatcher.main).launch {
                        adapter.notifyDataSetChanged()
                    }
                }
            })
    }
}

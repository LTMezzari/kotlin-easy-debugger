package mezzari.torres.lucas.easy_debugger_network.view.window

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import mezzari.torres.lucas.easy_debugger.di.appDispatcher
import mezzari.torres.lucas.easy_debugger.interfaces.AppDispatcher
import mezzari.torres.lucas.easy_debugger.source.model.ObservableList
import mezzari.torres.lucas.easy_debugger.generics.MinimizedWindow
import mezzari.torres.lucas.easy_debugger_network.databinding.FragmentNetworkLoggerBinding
import mezzari.torres.lucas.easy_debugger_network.interceptor.NetworkInterceptor
import mezzari.torres.lucas.easy_debugger_network.model.NetworkLog
import mezzari.torres.lucas.easy_debugger_network.view.adapter.NetworkLogAdapter

/**
 * @author Lucas T. Mezzari
 * @since 26/03/25
 **/
class NetworkLoggerWindow : MinimizedWindow() {

    private lateinit var binding: FragmentNetworkLoggerBinding

    private val adapter: NetworkLogAdapter by lazy {
        NetworkLogAdapter(context, networkLogs)
    }

    private val networkLogs: ObservableList<NetworkLog> by lazy {
        NetworkInterceptor.networkLogs
    }

    private val dispatcher: AppDispatcher by lazy {
        appDispatcher
    }

    private val listener: ObservableList.Listener<NetworkLog> by lazy {
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
        }
    }

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?): View {
        return FragmentNetworkLoggerBinding.inflate(inflater, parent, false).let {
            binding = it
            return@let binding.root
        }
    }

    override fun onViewCreated(view: View) {
        super.onViewCreated(view)

        binding.rvNetworkLogs.apply {
            adapter = this@NetworkLoggerWindow.adapter
            layoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }

        networkLogs.addListener(listener)
    }

    override fun onCreateLayoutParams(params: WindowManager.LayoutParams): WindowManager.LayoutParams {
        return super.onCreateLayoutParams(params).apply {
            height = 900
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        networkLogs.removeListener(listener)
    }
}
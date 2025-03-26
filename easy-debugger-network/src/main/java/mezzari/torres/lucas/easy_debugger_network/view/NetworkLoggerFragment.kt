package mezzari.torres.lucas.easy_debugger_network.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mezzari.torres.lucas.core.generic.BaseFragment
import mezzari.torres.lucas.easy_debugger_network.databinding.FragmentNetworkLoggerBinding
import mezzari.torres.lucas.easy_debugger_network.view.adapter.NetworkLogAdapter

/**
 * @author Lucas T. Mezzari
 * @since 21/02/2020
 **/
internal class NetworkLoggerFragment : BaseFragment() {

    private lateinit var binding: FragmentNetworkLoggerBinding

    private val adapter: NetworkLogAdapter by lazy {
        NetworkLogAdapter(requireContext())
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
    }
}

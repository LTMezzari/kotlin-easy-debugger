package mezzari.torres.lucas.easy_debugger.logs.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import mezzari.torres.lucas.easy_debugger.logs.view.adapter.LogAdapter
import mezzari.torres.lucas.easy_debugger.EasyDebugger
import mezzari.torres.lucas.easy_debugger.databinding.FragmentLogBinding
import mezzari.torres.lucas.core.generic.BaseFragment
import mezzari.torres.lucas.easy_debugger.logs.LogModule
import mezzari.torres.lucas.easy_debugger.logs.listener.LogListener

/**
 * @author Lucas T. Mezzari
 * @since 15/03/25
 **/
internal class LogFragment : BaseFragment() {

    private lateinit var binding: FragmentLogBinding

    private val adapter: LogAdapter by lazy {
        LogAdapter(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentLogBinding.inflate(inflater, container, false).let {
            binding = it
            return@let binding.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvLogs.adapter = adapter
        binding.rvLogs.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        lifecycleScope.launch {
            getLogListener()?.getLogs()?.collect {
                adapter.addLog(it)
            }
        }
    }

    private fun getLogListener(): LogListener? {
        val module = EasyDebugger.instance.getModuleByType<LogModule>()
        return module?.logListener
    }
}
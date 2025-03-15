package mezzari.torres.lucas.easy_debugger.debug

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mezzari.torres.lucas.easy_debugger.databinding.DialogDebugBinding
import mezzari.torres.lucas.easy_debugger.debug.adapter.DebugOptionsAdapter
import mezzari.torres.lucas.easy_debugger.debug.model.DebugOption
import mezzari.torres.lucas.easy_debugger.generic.BaseDialog
import mezzari.torres.lucas.easy_debugger.source.EasyDebugger

/**
 * @author Lucas T. Mezzari
 * @since 14/03/25
 **/
internal class DebugDialog : BaseDialog() {

    private lateinit var binding: DialogDebugBinding

    private val adapter: DebugOptionsAdapter by lazy {
        DebugOptionsAdapter(
            requireContext(),
            EasyDebugger.configuration.debugOptions,
            this::onDebugOptionClickLister
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return DialogDebugBinding.inflate(inflater, container, false).let {
            binding = it
            return@let binding.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvDebugOptions.adapter = adapter
        binding.rvDebugOptions.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    private fun onDebugOptionClickLister(option: DebugOption) {
        option.onClick.invoke(requireContext())
    }
}
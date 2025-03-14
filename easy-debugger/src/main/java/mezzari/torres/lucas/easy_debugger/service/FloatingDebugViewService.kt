package mezzari.torres.lucas.easy_debugger.service

import android.view.*
import android.view.Gravity.*
import mezzari.torres.lucas.easy_debugger.R
import mezzari.torres.lucas.easy_debugger.databinding.LayoutFloatingDebugViewBinding
import mezzari.torres.lucas.easy_debugger.generic.BaseFloatingViewService
import mezzari.torres.lucas.easy_debugger.source.EasyDebugger


/**
 * @author Lucas T. Mezzari
 * @author lucas.mezzari@operacao.rcadigital.com.br
 * @since 2020-02-20
 */
class FloatingDebugViewService : BaseFloatingViewService() {

    private lateinit var binding: LayoutFloatingDebugViewBinding

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?): View {
        return LayoutFloatingDebugViewBinding.inflate(
            inflater,
            parent,
            false
        ).let {
            binding = it
            return@let it.root
        }
    }

    override fun onCreateLayoutParams(): WindowManager.LayoutParams {
        val params = super.onCreateLayoutParams()
        params.gravity = END or BOTTOM
        return params
    }

    override fun onViewCreated(view: View) {
        super.onViewCreated(view)
        binding.rlDebugContainer.setOnClickListener {
            EasyDebugger.configuration.onFloatingViewClickListener?.invoke(it)
        }
    }
}
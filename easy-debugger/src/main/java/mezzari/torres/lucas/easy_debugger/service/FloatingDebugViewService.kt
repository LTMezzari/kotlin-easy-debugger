package mezzari.torres.lucas.easy_debugger.service

import android.view.*
import android.view.Gravity.*
import kotlinx.android.synthetic.main.layout_floating_debug_view.view.*
import mezzari.torres.lucas.easy_debugger.R
import mezzari.torres.lucas.easy_debugger.generic.BaseFloatingViewService
import mezzari.torres.lucas.easy_debugger.source.EasyDebugger


/**
 * @author Lucas T. Mezzari
 * @author lucas.mezzari@operacao.rcadigital.com.br
 * @since 2020-02-20
 */
class FloatingDebugViewService: BaseFloatingViewService() {

    private lateinit var floatingDebugView: View

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?): View {
        return inflater.inflate(R.layout.layout_floating_debug_view, parent)
    }

    override fun onCreateLayoutParams(): WindowManager.LayoutParams {
        val params = super.onCreateLayoutParams()
        params.gravity = END or BOTTOM
        return params
    }

    override fun onViewCreated(view: View) {
        super.onViewCreated(view)
        floatingDebugView = view

        floatingDebugView.rlDebugContainer.setOnClickListener {
            EasyDebugger.configuration.onFloatingViewClickListener?.invoke(it)
        }
    }
}
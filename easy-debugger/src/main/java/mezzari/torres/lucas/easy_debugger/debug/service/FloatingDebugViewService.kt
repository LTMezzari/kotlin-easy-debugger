package mezzari.torres.lucas.easy_debugger.debug.service

import android.view.*
import android.view.Gravity.*
import androidx.appcompat.app.AppCompatActivity
import mezzari.torres.lucas.easy_debugger.databinding.LayoutFloatingDebugViewBinding
import mezzari.torres.lucas.easy_debugger.debug.dialog.DebugDialog
import mezzari.torres.lucas.easy_debugger.generic.BaseFloatingViewService
import mezzari.torres.lucas.easy_debugger.EasyDebugger
import mezzari.torres.lucas.easy_debugger.debug.FloatingDebugViewModule
import mezzari.torres.lucas.easy_debugger.navigation.ActivityNavigationModule


/**
 * @author Lucas T. Mezzari
 * @since 2020-02-20
 */
internal class FloatingDebugViewService : BaseFloatingViewService() {

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
            val debugger = EasyDebugger.instance
            val module =
                debugger.getModuleByType<FloatingDebugViewModule>() ?: return@setOnClickListener
            val activity =
                module.currentActivity as? AppCompatActivity
                    ?: return@setOnClickListener
            DebugDialog(debugger.debugOptions).show(
                activity.supportFragmentManager,
                DebugDialog::class.java.name
            )
        }
    }
}
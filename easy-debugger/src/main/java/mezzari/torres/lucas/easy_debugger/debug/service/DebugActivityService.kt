package mezzari.torres.lucas.easy_debugger.debug.service

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import mezzari.torres.lucas.easy_debugger.databinding.LayoutMinimizedDebugViewBinding
import mezzari.torres.lucas.core.generic.BaseFloatingViewService
import mezzari.torres.lucas.easy_debugger.EasyDebugger
import mezzari.torres.lucas.easy_debugger.debug.DebugModule
import mezzari.torres.lucas.easy_debugger.debug.view.DebugActivity
import mezzari.torres.lucas.easy_debugger.interfaces.DebuggerModule
import mezzari.torres.lucas.easy_debugger.generics.MinimizedWindow

/**
 * @author Lucas T. Mezzari
 * @since 22/03/25
 **/
internal class DebugActivityService : BaseFloatingViewService() {

    private lateinit var binding: LayoutMinimizedDebugViewBinding
    private var window: MinimizedWindow? = null

    override fun onCreate() {
        super.onCreate()
        val module = getDebuggerModule() ?: return
        window = module.onCreateMinimizedWindow()
        window?.onCreate(applicationContext)
    }

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?): View {
        binding = LayoutMinimizedDebugViewBinding.inflate(inflater, parent, false)
        inflateDebugView(inflater, binding.flContent)
        return binding.root
    }

    override fun onViewCreated(view: View) {
        super.onViewCreated(view)
        binding.ibClose.setOnClickListener {
            stopSelf()
        }
        binding.ibMaximize.setOnClickListener {
            stopSelf()
            startActivity(Intent(applicationContext, DebugActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            })
        }
        window?.onViewCreated(view)
    }

    override fun onDestroy() {
        super.onDestroy()
        window?.onDestroy()
    }

    override fun onCreateLayoutParams(): WindowManager.LayoutParams {
        val params = super.onCreateLayoutParams()
        return window?.onCreateLayoutParams(params) ?: params
    }

    private fun inflateDebugView(inflater: LayoutInflater, parent: ViewGroup) {
        val view = window?.onCreateView(inflater, parent) ?: return
        parent.addView(view)
    }

    private fun getDebuggerModule(): DebuggerModule? {
        return EasyDebugger.instance.getModuleByType<DebugModule>()?.currentActiveModule
    }
}
package mezzari.torres.lucas.easy_debugger.logs.view.window

import android.content.Context
import android.view.Gravity.BOTTOM
import android.view.Gravity.END
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mezzari.torres.lucas.easy_debugger.EasyDebugger
import mezzari.torres.lucas.easy_debugger.databinding.LayoutMinimizedLogFragmentBinding
import mezzari.torres.lucas.easy_debugger.interfaces.MinimizedWindow
import mezzari.torres.lucas.easy_debugger.logs.LogModule
import mezzari.torres.lucas.easy_debugger.logs.listener.LogListener
import mezzari.torres.lucas.easy_debugger.logs.view.adapter.LogAdapter

/**
 * @author Lucas T. Mezzari
 * @since 22/03/25
 **/
class LogWindow : MinimizedWindow {

    private lateinit var context: Context
    private lateinit var binding: LayoutMinimizedLogFragmentBinding

    private val adapter: LogAdapter by lazy {
        LogAdapter(context)
    }

    override fun onCreate(context: Context) {
        super.onCreate(context)
        this.context = context
    }

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?): View {
        return LayoutMinimizedLogFragmentBinding.inflate(inflater, parent, false).let {
            binding = it
            return@let binding.root
        }
    }

    override fun onViewCreated(view: View) {
        super.onViewCreated(view)
        binding.rvLogs.adapter = adapter
        binding.rvLogs.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        CoroutineScope(Dispatchers.Main).launch {
            getLogListener()?.getLogs()?.collect {
                adapter.addLog(it)
            }
        }
    }

    override fun onCreateLayoutParams(params: WindowManager.LayoutParams): WindowManager.LayoutParams {
        params.gravity = BOTTOM
        return params
    }

    private fun getLogListener(): LogListener? {
        val module = EasyDebugger.instance.getModuleByType<LogModule>()
        return module?.logListener
    }
}
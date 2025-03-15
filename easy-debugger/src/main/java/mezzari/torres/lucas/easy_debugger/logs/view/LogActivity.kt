package mezzari.torres.lucas.easy_debugger.logs.view

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import mezzari.torres.lucas.easy_debugger.databinding.ActivityLogBinding
import mezzari.torres.lucas.easy_debugger.generic.BaseActivity
import mezzari.torres.lucas.easy_debugger.logs.view.adapter.LogAdapter
import mezzari.torres.lucas.easy_debugger.source.EasyDebugger

/**
 * @author Lucas T. Mezzari
 * @since 15/03/25
 **/
internal class LogActivity : BaseActivity() {

    private lateinit var binding: ActivityLogBinding

    private val adapter: LogAdapter by lazy {
        LogAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvLogs.adapter = adapter
        binding.rvLogs.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        lifecycleScope.launch {
//            EasyDebugger.configuration.logListener.startListening()
            EasyDebugger.configuration.logListener.getLogs()?.collect {
                adapter.addLog(it)
            }
        }
    }
}
package mezzari.torres.lucas.easy_debugger.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mezzari.torres.lucas.easy_debugger.R
import mezzari.torres.lucas.easy_debugger.adapter.NetworkLogAdapter
import mezzari.torres.lucas.easy_debugger.databinding.ActivityNetworkLoggerBinding

class NetworkLoggerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNetworkLoggerBinding

    private val adapter: NetworkLogAdapter by lazy {
        NetworkLogAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNetworkLoggerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Network Logs"

        binding.rvNetworkLogs.apply {
            adapter = this@NetworkLoggerActivity.adapter
            layoutManager =
                LinearLayoutManager(this@NetworkLoggerActivity, RecyclerView.VERTICAL, false)
        }
    }
}

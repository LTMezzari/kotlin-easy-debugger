package mezzari.torres.lucas.easy_debugger_network.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mezzari.torres.lucas.easy_debugger_network.view.adapter.NetworkLogAdapter
import mezzari.torres.lucas.easy_debugger.databinding.ActivityNetworkLoggerBinding

/**
 * @author Lucas T. Mezzari
 * @since 21/02/2020
 **/
internal class NetworkLoggerActivity : AppCompatActivity() {

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

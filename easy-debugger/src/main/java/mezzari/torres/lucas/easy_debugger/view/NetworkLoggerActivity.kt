package mezzari.torres.lucas.easy_debugger.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_network_logger.*
import mezzari.torres.lucas.easy_debugger.R
import mezzari.torres.lucas.easy_debugger.adapter.NetworkLogAdapter

class NetworkLoggerActivity : AppCompatActivity() {

    private val adapter: NetworkLogAdapter by lazy {
        NetworkLogAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network_logger)

        title = "Network Logs"

        rvNetworkLogs.apply {
            adapter = this@NetworkLoggerActivity.adapter
            layoutManager = LinearLayoutManager(this@NetworkLoggerActivity, RecyclerView.VERTICAL, false)
        }
    }
}

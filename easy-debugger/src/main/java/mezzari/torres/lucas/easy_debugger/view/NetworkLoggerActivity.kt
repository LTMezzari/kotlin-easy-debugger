package mezzari.torres.lucas.easy_debugger.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_network_logger.*
import mezzari.torres.lucas.easy_debugger.R

class NetworkLoggerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network_logger)

        rvNetworkLogs.apply {
            layoutManager = LinearLayoutManager(this@NetworkLoggerActivity, RecyclerView.VERTICAL, false)
        }
    }
}

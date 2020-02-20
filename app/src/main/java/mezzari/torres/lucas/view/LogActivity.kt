package mezzari.torres.lucas.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_log.*
import mezzari.torres.lucas.R
import mezzari.torres.lucas.easy_debugger.source.network.NetworkLoggerModule

class LogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log)

        tvLog.text = NetworkLoggerModule.networkLogs.toString()
    }
}

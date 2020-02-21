package mezzari.torres.lucas.easy_debugger.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import kotlinx.android.synthetic.main.activity_exception.*
import mezzari.torres.lucas.easy_debugger.R
import java.io.PrintWriter
import java.io.StringWriter

class ExceptionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exception)

        if (intent.hasExtra("exception")) {
            val exception: Throwable = intent.getSerializableExtra("exception") as Throwable
            val sw = StringWriter()
            exception.printStackTrace(PrintWriter(sw))
            tvException.text = sw.toString()
        }

        tvException.movementMethod = ScrollingMovementMethod()
    }
}

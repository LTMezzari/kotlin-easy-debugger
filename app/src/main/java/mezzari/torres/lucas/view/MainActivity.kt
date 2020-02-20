package mezzari.torres.lucas.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import mezzari.torres.lucas.R
import java.lang.RuntimeException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnRequestCEP.setOnClickListener {
            startActivity(Intent(this, CepActivity::class.java))
        }

        btnRequestJokes.setOnClickListener {
            startActivity(Intent(this, JokesActivity::class.java))
        }

        btnLogs.setOnClickListener {
            startActivity(Intent(this, LogActivity::class.java))
        }

        btnExplode.setOnClickListener {
            throw RuntimeException("User pressed the wrong button")
        }
    }
}

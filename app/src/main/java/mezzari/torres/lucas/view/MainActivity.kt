package mezzari.torres.lucas.view

import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import mezzari.torres.lucas.R
import mezzari.torres.lucas.generic.BaseActivity
import java.lang.RuntimeException

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnRequestCEP.setOnClickListener {
            startActivity(Intent(this, CepActivity::class.java))
        }

        btnRequestJokes.setOnClickListener {
            startActivity(Intent(this, JokesActivity::class.java))
        }

        btnExplode.setOnClickListener {
            throw RuntimeException("User pressed the wrong button")
        }
    }
}

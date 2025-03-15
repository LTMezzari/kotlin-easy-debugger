package mezzari.torres.lucas.view

import android.content.Intent
import android.os.Bundle
import mezzari.torres.lucas.databinding.ActivityMainBinding
import mezzari.torres.lucas.generic.BaseActivity
import java.lang.RuntimeException

/**
 * @author Lucas T. Mezzari
 * @since 21/02/2020
 **/
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRequestCEP.setOnClickListener {
            startActivity(Intent(this, CepActivity::class.java))
        }

        binding.btnRequestJokes.setOnClickListener {
            startActivity(Intent(this, JokesActivity::class.java))
        }

        binding.btnExplode.setOnClickListener {
            throw RuntimeException("User pressed the wrong button")
        }
    }
}

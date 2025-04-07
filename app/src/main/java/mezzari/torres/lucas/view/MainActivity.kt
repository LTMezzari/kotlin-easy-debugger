package mezzari.torres.lucas.view

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import mezzari.torres.lucas.R
import mezzari.torres.lucas.easy_debugger.generic.BaseActivity
import mezzari.torres.lucas.databinding.ActivityMainBinding

/**
 * @author Lucas T. Mezzari
 * @since 21/02/2020
 **/
class MainActivity : BaseActivity() {

    private val navController: NavController? by lazy {
        supportFragmentManager.findFragmentById(R.id.fcvNavHost)?.findNavController()
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        navController?.also {
            binding.toolbar.setupWithNavController(it)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController?.navigateUp() ?: super.onSupportNavigateUp()
    }
}

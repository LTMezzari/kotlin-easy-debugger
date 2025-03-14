package mezzari.torres.lucas.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import mezzari.torres.lucas.R
import mezzari.torres.lucas.databinding.ActivityJokesBinding
import mezzari.torres.lucas.generic.BaseActivity
import mezzari.torres.lucas.model.Joke
import mezzari.torres.lucas.network.jokes.JokesService

class JokesActivity : BaseActivity() {

    private lateinit var binding: ActivityJokesBinding

    private val service = JokesService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJokesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSend.setOnClickListener {
            val type = binding.tvJokeType.text.toString()

            if (type.isNotEmpty()) {
                service.getRandomJokeByType(type).then { response ->
                    showJoke(response?.get(0))
                }.catch {
                    Toast.makeText(this@JokesActivity, "Error", Toast.LENGTH_LONG).show()
                }
            } else {
                service.getRandomJoke().then { response ->
                    showJoke(response)
                }.catch {
                    Toast.makeText(this@JokesActivity, "Error", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun showJoke(joke: Joke?) {
        joke?.run {
            AlertDialog.Builder(this@JokesActivity)
                .setTitle(setup)
                .setMessage(punchline)
                .setPositiveButton("Ok", null)
                .show()
        }
    }
}

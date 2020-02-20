package mezzari.torres.lucas.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_jokes.*
import mezzari.torres.lucas.R
import mezzari.torres.lucas.model.Joke
import mezzari.torres.lucas.network.jokes.JokesService
import mezzari.torres.lucas.persistence.SessionManager

class JokesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jokes)

        spUrls.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(adapter: AdapterView<*>?) {}

            override fun onItemSelected(adapter: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val url = spUrls.getItemAtPosition(position) as? String ?: return
                SessionManager.baseUrl = url
            }
        }

        btnSend.setOnClickListener {
            val type = tvJokeType.text.toString()

            if (type.isNotEmpty()) {
                JokesService().getRandomJokeByType(type).then { response ->
                    showJoke(response?.get(0))
                }.catch {
                    Toast.makeText(this@JokesActivity, "Error", Toast.LENGTH_LONG).show()
                }
            } else {
                JokesService().getRandomJoke().then { response ->
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

package mezzari.torres.lucas.view.jokes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import mezzari.torres.lucas.databinding.FragmentJokesBinding
import mezzari.torres.lucas.generic.BaseFragment
import mezzari.torres.lucas.model.Joke
import mezzari.torres.lucas.network.jokes.JokesService

class JokesFragment : BaseFragment() {

    private lateinit var binding: FragmentJokesBinding

    private val service = JokesService()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentJokesBinding.inflate(inflater, container, false).let {
            binding = it
            return@let binding.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSend.setOnClickListener {
            val type = binding.tvJokeType.text.toString()

            if (type.isNotEmpty()) {
                service.getRandomJokeByType(type).then { response ->
                    showJoke(response?.get(0))
                }.catch {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show()
                }
            } else {
                service.getRandomJoke().then { response ->
                    showJoke(response)
                }.catch {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show()
                }
            }
        }

    }

    private fun showJoke(joke: Joke?) {
        joke?.run {
            AlertDialog.Builder(requireContext())
                .setTitle(setup)
                .setMessage(punchline)
                .setPositiveButton("Ok", null)
                .show()
        }
    }
}

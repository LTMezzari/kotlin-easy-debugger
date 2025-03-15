package mezzari.torres.lucas.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import mezzari.torres.lucas.R
import mezzari.torres.lucas.databinding.FragmentMainBinding
import mezzari.torres.lucas.generic.BaseFragment
import java.lang.RuntimeException

/**
 * @author Lucas T. Mezzari
 * @since 15/03/25
 **/
class MainFragment : BaseFragment() {
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentMainBinding.inflate(inflater, container, false).let {
            binding = it
            return@let binding.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnRequestCEP.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_cepFragment)
        }

        binding.btnRequestJokes.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_jokesFragment)
        }

        binding.btnExplode.setOnClickListener {
            throw RuntimeException("User pressed the wrong button")
        }
    }
}
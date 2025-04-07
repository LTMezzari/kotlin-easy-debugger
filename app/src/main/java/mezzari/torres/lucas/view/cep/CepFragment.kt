package mezzari.torres.lucas.view.cep

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import mezzari.torres.lucas.easy_debugger.generic.BaseFragment
import mezzari.torres.lucas.databinding.FragmentCepBinding
import mezzari.torres.lucas.network.viacep.ViacepService

/**
 * @author Lucas T. Mezzari
 * @since 22/03/2025
 **/
class CepFragment : BaseFragment() {

    private lateinit var binding: FragmentCepBinding

    private val service = ViacepService()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentCepBinding.inflate(inflater, container, false).let {
            binding = it
            return@let binding.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSend.setOnClickListener {
            val cep = binding.tvCep.text.toString()
            service.getCep(cep).then {
                Toast.makeText(requireContext(), "Ok", Toast.LENGTH_LONG).show()
            }.catch {
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show()
            }
        }
    }
}

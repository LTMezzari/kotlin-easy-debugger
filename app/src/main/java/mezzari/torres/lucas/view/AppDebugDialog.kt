package mezzari.torres.lucas.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.DialogFragment
import mezzari.torres.lucas.databinding.DialogDebugBinding
import mezzari.torres.lucas.persistence.SessionManager

/**
 * @author Lucas T. Mezzari
 * @author lucas.mezzari@operacao.rcadigital.com.br
 * @since 2020-02-21
 */
class AppDebugDialog : DialogFragment() {

    private lateinit var binding: DialogDebugBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return DialogDebugBinding.inflate(inflater, container, false).let {
            binding = it
            return@let it.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.spUrls.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(adapter: AdapterView<*>?) {}

            override fun onItemSelected(
                adapter: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val url = binding.spUrls.getItemAtPosition(position) as? String ?: return
                SessionManager.baseUrl = url
            }
        }
    }
}
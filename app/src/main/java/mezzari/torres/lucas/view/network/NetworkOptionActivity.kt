package mezzari.torres.lucas.view.network

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import mezzari.torres.lucas.databinding.ActivityNetworkOptionBinding
import mezzari.torres.lucas.generic.BaseActivity
import mezzari.torres.lucas.persistence.SessionManager

/**
 * @author Lucas T. Mezzari
 * @since 16/03/25
 **/
class NetworkOptionActivity : BaseActivity() {
    private lateinit var binding: ActivityNetworkOptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNetworkOptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
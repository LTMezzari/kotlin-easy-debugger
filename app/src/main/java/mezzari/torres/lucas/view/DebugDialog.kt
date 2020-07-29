package mezzari.torres.lucas.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.dialog_debug.*
import mezzari.torres.lucas.R
import mezzari.torres.lucas.easy_debugger.view.NetworkLoggerActivity
import mezzari.torres.lucas.persistence.SessionManager

/**
 * @author Lucas T. Mezzari
 * @author lucas.mezzari@operacao.rcadigital.com.br
 * @since 2020-02-21
 */
class DebugDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_debug, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spUrls.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(adapter: AdapterView<*>?) {}

            override fun onItemSelected(
                adapter: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val url = spUrls.getItemAtPosition(position) as? String ?: return
                SessionManager.baseUrl = url
            }
        }

        tvLogs.setOnClickListener {
            startActivity(
                Intent(context, NetworkLoggerActivity::class.java)
            )
            dismiss()
        }
    }
}
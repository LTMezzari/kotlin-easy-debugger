package mezzari.torres.lucas.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_cep.*
import mezzari.torres.lucas.R
import mezzari.torres.lucas.network.viacep.ViacepService
import mezzari.torres.lucas.persistence.SessionManager

class CepActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cep)

        spUrls.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(adapter: AdapterView<*>?) {}

            override fun onItemSelected(adapter: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val url = spUrls.getItemAtPosition(position) as? String ?: return
                SessionManager.baseUrl = url
            }
        }

        btnSend.setOnClickListener {
            val cep = tvCep.text.toString()
            ViacepService().getCep(cep).then {
                Toast.makeText(this@CepActivity, "Ok", Toast.LENGTH_LONG).show()
                finish()
            }.catch {
                Toast.makeText(this@CepActivity, "Error", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }
}

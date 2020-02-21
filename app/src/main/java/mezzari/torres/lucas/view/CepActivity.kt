package mezzari.torres.lucas.view

import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_cep.*
import mezzari.torres.lucas.R
import mezzari.torres.lucas.generic.BaseActivity
import mezzari.torres.lucas.network.viacep.ViacepService

class CepActivity : BaseActivity() {

    private val service = ViacepService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cep)

        btnSend.setOnClickListener {
            val cep = tvCep.text.toString()
            service.getCep(cep).then {
                Toast.makeText(this@CepActivity, "Ok", Toast.LENGTH_LONG).show()
                finish()
            }.catch {
                Toast.makeText(this@CepActivity, "Error", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }
}

package mezzari.torres.lucas.view

import android.os.Bundle
import android.widget.Toast
import mezzari.torres.lucas.R
import mezzari.torres.lucas.databinding.ActivityCepBinding
import mezzari.torres.lucas.generic.BaseActivity
import mezzari.torres.lucas.network.viacep.ViacepService

class CepActivity : BaseActivity() {

    private lateinit var binding: ActivityCepBinding

    private val service = ViacepService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCepBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSend.setOnClickListener {
            val cep = binding.tvCep.text.toString()
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

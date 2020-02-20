package mezzari.torres.lucas.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_cep.*
import mezzari.torres.lucas.R
import mezzari.torres.lucas.network.annotation.Route
import mezzari.torres.lucas.network.source.Network
import mezzari.torres.lucas.network.source.promise.NetworkPromise
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

class CepActivity : AppCompatActivity() {

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

    @Route("https://viacep.com.br/ws/")
    interface IViacepAPI {
        @GET("{cep}/json/")
        fun getCep(
            @Path("cep") cep: String
        ): Call<Void>
    }

    class ViacepService {
        private val api: IViacepAPI = Network.build()

        fun getCep(cep: String): NetworkPromise<Void> {
            return NetworkPromise {
                api.getCep(cep).enqueue(this)
            }
        }
    }
}

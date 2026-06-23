package pe.edu.cibertec.appgasmapocho.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import pe.edu.cibertec.appgasmapocho.R
import pe.edu.cibertec.appgasmapocho.databinding.MetodoPagoBinding
import pe.edu.cibertec.appgasmapocho.retrofit.ClienteRetrofit
import pe.edu.cibertec.appgasmapocho.retrofit.response.PedidoRequest
import pe.edu.cibertec.appgasmapocho.retrofit.response.PedidoResponse
import pe.edu.cibertec.appgasmapocho.util.PedidoEnProceso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MetodoPagoActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: MetodoPagoBinding
    private var metodoSeleccionado = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = MetodoPagoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.cardEfectivo.setOnClickListener(this)
        binding.cardTransferencia.setOnClickListener(this)
        binding.cardTarjeta.setOnClickListener(this)
        binding.btnContinuar.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.cardEfectivo -> {
                metodoSeleccionado = "Efectivo"
                Toast.makeText(this, "Pago: Efectivo", Toast.LENGTH_SHORT).show()
            }
            R.id.cardTransferencia -> {
                metodoSeleccionado = "Transferencia"
                Toast.makeText(this, "Pago: Transferencia", Toast.LENGTH_SHORT).show()
            }
            R.id.cardTarjeta -> {
                metodoSeleccionado = "Tarjeta"
                Toast.makeText(this, "Pago: Tarjeta", Toast.LENGTH_SHORT).show()
            }
            R.id.btnContinuar -> confirmarPedido()
        }
    }

    private fun confirmarPedido() {
        if (metodoSeleccionado.isEmpty()) {
            Toast.makeText(this, "Selecciona un metodo de pago", Toast.LENGTH_SHORT).show()
        } else {
            PedidoEnProceso.metodoPago = metodoSeleccionado

            val formato = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val fecha = formato.format(Date())

            val body = PedidoRequest(
                PedidoEnProceso.clienteNombre,
                PedidoEnProceso.clienteDireccion,
                PedidoEnProceso.productoId,
                PedidoEnProceso.metodoPago,
                fecha
            )

            val request: Call<PedidoResponse> = ClienteRetrofit.gasMapochoService.crearPedido(body)
            request.enqueue(object : Callback<PedidoResponse> {
                override fun onResponse(p0: Call<PedidoResponse>, p1: Response<PedidoResponse>) {
                    if (p1.isSuccessful) {
                        val intent = Intent(this@MetodoPagoActivity, PedidoRealizadoActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@MetodoPagoActivity, "No se pudo registrar el pedido", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(p0: Call<PedidoResponse>, p1: Throwable) {
                    Toast.makeText(this@MetodoPagoActivity, "No se pudo conectar al servidor", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}

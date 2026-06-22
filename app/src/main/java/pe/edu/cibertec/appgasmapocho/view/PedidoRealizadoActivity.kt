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
import pe.edu.cibertec.appgasmapocho.databinding.PedidoRealizadoBinding
import pe.edu.cibertec.appgasmapocho.util.PedidoEnProceso

class PedidoRealizadoActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: PedidoRealizadoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = PedidoRealizadoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnVerEstado.setOnClickListener(this)
        binding.btnVolverInicio.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.btnVerEstado -> Toast.makeText(this, "Tu pedido esta pendiente", Toast.LENGTH_SHORT).show()
            R.id.btnVolverInicio -> volverAlInicio()
        }
    }

    private fun volverAlInicio() {
        // empezamos un pedido nuevo desde cero
        PedidoEnProceso.limpiar()
        val intent = Intent(this, SeleccionarCompraActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}

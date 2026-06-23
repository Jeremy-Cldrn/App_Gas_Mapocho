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
import pe.edu.cibertec.appgasmapocho.databinding.ActivityDatosEnvioBinding
import pe.edu.cibertec.appgasmapocho.util.PedidoEnProceso

class DatosEnvioActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDatosEnvioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDatosEnvioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btncontinuar.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        continuar()
    }

    private fun continuar() {
        val nombres = binding.etnombres.text.toString()
        val direccion = binding.etdireccion.text.toString()

        if (nombres.isEmpty()) {
            Toast.makeText(this, "Ingresa tus nombres", Toast.LENGTH_SHORT).show()
        } else {
            if (direccion.isEmpty()) {
                Toast.makeText(this, "Ingresa tu direccion", Toast.LENGTH_SHORT).show()
            } else {
                PedidoEnProceso.clienteNombre = nombres
                PedidoEnProceso.clienteDireccion = direccion
                val intent = Intent(this, MetodoPagoActivity::class.java)
                startActivity(intent)
            }
        }
    }
}

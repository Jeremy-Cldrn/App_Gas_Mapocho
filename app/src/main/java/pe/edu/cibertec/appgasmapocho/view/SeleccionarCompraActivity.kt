package pe.edu.cibertec.appgasmapocho.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import pe.edu.cibertec.appgasmapocho.R
import pe.edu.cibertec.appgasmapocho.databinding.SeleccionarCompraBinding
import pe.edu.cibertec.appgasmapocho.retrofit.ClienteRetrofit
import pe.edu.cibertec.appgasmapocho.retrofit.response.Producto
import pe.edu.cibertec.appgasmapocho.view.adapter.ProductoAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SeleccionarCompraActivity : AppCompatActivity() {

    private lateinit var binding: SeleccionarCompraBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = SeleccionarCompraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.rvpedidos.layoutManager = LinearLayoutManager(this)
        cargarProductos()
    }

    private fun cargarProductos() {
        val request: Call<List<Producto>> = ClienteRetrofit.gasMapochoService.listarInventario()
        request.enqueue(object : Callback<List<Producto>> {
            override fun onResponse(p0: Call<List<Producto>>, p1: Response<List<Producto>>) {
                if (p1.isSuccessful) {
                    binding.rvpedidos.adapter = ProductoAdapter(p1.body()!!)
                } else {
                    Toast.makeText(this@SeleccionarCompraActivity, "No se pudo cargar el catalogo", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(p0: Call<List<Producto>>, p1: Throwable) {
                Toast.makeText(this@SeleccionarCompraActivity, "No se pudo conectar al servidor", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

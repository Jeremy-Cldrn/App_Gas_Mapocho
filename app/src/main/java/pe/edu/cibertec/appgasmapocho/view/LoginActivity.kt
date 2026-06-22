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
import pe.edu.cibertec.appgasmapocho.databinding.ActivityLoginBinding
import pe.edu.cibertec.appgasmapocho.retrofit.ClienteRetrofit
import pe.edu.cibertec.appgasmapocho.retrofit.response.LoginRequest
import pe.edu.cibertec.appgasmapocho.retrofit.response.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.button.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        iniciarSesion()
    }

    private fun iniciarSesion() {
        val correo = binding.etCorreo.text.toString()
        val clave = binding.etPassword.text.toString()

        if (correo.isEmpty()) {
            Toast.makeText(this, "Ingresa tu correo", Toast.LENGTH_SHORT).show()
        } else {
            if (clave.isEmpty()) {
                Toast.makeText(this, "Ingresa tu contrasena", Toast.LENGTH_SHORT).show()
            } else {
                val body = LoginRequest(correo, clave)
                val request: Call<LoginResponse> = ClienteRetrofit.gasMapochoService.login(body)
                request.enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(p0: Call<LoginResponse>, p1: Response<LoginResponse>) {
                        if (p1.isSuccessful) {
                            val intent = Intent(this@LoginActivity, SeleccionarCompraActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this@LoginActivity, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(p0: Call<LoginResponse>, p1: Throwable) {
                        Toast.makeText(this@LoginActivity, "No se pudo conectar al servidor", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }
}

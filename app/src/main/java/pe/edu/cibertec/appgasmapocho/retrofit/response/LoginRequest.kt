package pe.edu.cibertec.appgasmapocho.retrofit.response

// lo que mandamos al backend para iniciar sesion
data class LoginRequest(
    val email: String,
    val password: String
)

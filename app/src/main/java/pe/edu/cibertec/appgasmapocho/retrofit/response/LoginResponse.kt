package pe.edu.cibertec.appgasmapocho.retrofit.response

// lo que responde el backend cuando el login sale bien
data class LoginResponse(
    val token: String,
    val rol: String,
    val email: String
)

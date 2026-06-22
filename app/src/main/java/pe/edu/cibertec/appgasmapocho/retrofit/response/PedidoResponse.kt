package pe.edu.cibertec.appgasmapocho.retrofit.response

// lo que responde el backend cuando el pedido se guarda
data class PedidoResponse(
    val id: Int,
    val estado: String
)

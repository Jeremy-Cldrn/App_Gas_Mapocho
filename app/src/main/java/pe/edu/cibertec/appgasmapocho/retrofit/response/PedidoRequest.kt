package pe.edu.cibertec.appgasmapocho.retrofit.response

// lo que mandamos al backend para crear un pedido
// los nombres van en snake_case para que calcen con el JSON del backend
data class PedidoRequest(
    val cliente_nombre: String,
    val cliente_direccion: String,
    val producto_id: Int,
    val metodo_pago: String,
    val fecha: String
)

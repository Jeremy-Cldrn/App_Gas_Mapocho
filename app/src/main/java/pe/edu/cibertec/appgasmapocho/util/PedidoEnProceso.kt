package pe.edu.cibertec.appgasmapocho.util

// Guarda los datos del pedido mientras el usuario pasa por las pantallas.
// Asi no tenemos que mandar todo por Intent en cada paso.
object PedidoEnProceso {
    var productoId = 0
    var productoNombre = ""
    var productoPrecio = 0.0
    var clienteNombre = ""
    var clienteDireccion = ""
    var metodoPago = ""

    // deja todo en blanco para empezar un pedido nuevo
    fun limpiar() {
        productoId = 0
        productoNombre = ""
        productoPrecio = 0.0
        clienteNombre = ""
        clienteDireccion = ""
        metodoPago = ""
    }
}

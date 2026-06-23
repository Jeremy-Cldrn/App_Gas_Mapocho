package pe.edu.cibertec.appgasmapocho.retrofit.response

// un producto del inventario tal como llega del backend
data class Producto(
    val id: Int,
    val nombre: String,
    val codigo: String,
    val stock: Int,
    val precio: Double
)

package pe.edu.cibertec.appgasmapocho.retrofit.api

import pe.edu.cibertec.appgasmapocho.retrofit.response.LoginRequest
import pe.edu.cibertec.appgasmapocho.retrofit.response.LoginResponse
import pe.edu.cibertec.appgasmapocho.retrofit.response.PedidoRequest
import pe.edu.cibertec.appgasmapocho.retrofit.response.PedidoResponse
import pe.edu.cibertec.appgasmapocho.retrofit.response.Producto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

// Aca declaramos los endpoints que usa la app
interface IGasMapochoService {

    @POST("login")
    fun login(@Body body: LoginRequest): Call<LoginResponse>

    @GET("inventario")
    fun listarInventario(): Call<List<Producto>>

    @POST("pedidos")
    fun crearPedido(@Body body: PedidoRequest): Call<PedidoResponse>
}

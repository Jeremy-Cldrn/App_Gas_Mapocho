package pe.edu.cibertec.appgasmapocho.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pe.edu.cibertec.appgasmapocho.R
import pe.edu.cibertec.appgasmapocho.databinding.CardPedidoBinding
import pe.edu.cibertec.appgasmapocho.retrofit.response.Producto
import pe.edu.cibertec.appgasmapocho.util.PedidoEnProceso
import pe.edu.cibertec.appgasmapocho.view.DatosEnvioActivity

class ProductoAdapter(private var lista: List<Producto>) :
    RecyclerView.Adapter<ProductoAdapter.ViewHolder>(), View.OnClickListener {

    inner class ViewHolder(val binding: CardPedidoBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val binding = CardPedidoBinding.inflate(LayoutInflater.from(p0.context), p0, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val producto = lista[p1]
        p0.binding.tvtitulocardpedido.text = producto.nombre
        p0.binding.tvpreciocardpedido.text = "$ " + producto.precio
        p0.binding.imageView.setImageResource(obtenerImagen(producto.codigo))

        // guardamos la posicion en el boton para saber cual balon se toco
        p0.binding.btnagregar.tag = p1
        p0.binding.btnagregar.setOnClickListener(this)
    }

    override fun getItemCount(): Int = lista.size

    override fun onClick(p0: View?) {
        val posicion = p0!!.tag as Int
        val producto = lista[posicion]
        PedidoEnProceso.productoId = producto.id
        PedidoEnProceso.productoNombre = producto.nombre
        PedidoEnProceso.productoPrecio = producto.precio
        val intent = Intent(p0.context, DatosEnvioActivity::class.java)
        p0.context.startActivity(intent)
    }

    // elige la imagen del balon segun el codigo del producto
    private fun obtenerImagen(codigo: String): Int {
        var imagen = R.drawable.balon_15kg
        if (codigo == "CG5K") {
            imagen = R.drawable.balon_5kg
        } else if (codigo == "CG11K") {
            imagen = R.drawable.balon_11kg
        }
        return imagen
    }
}

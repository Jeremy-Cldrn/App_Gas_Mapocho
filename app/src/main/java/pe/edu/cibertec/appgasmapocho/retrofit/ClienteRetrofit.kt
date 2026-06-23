package pe.edu.cibertec.appgasmapocho.retrofit

import okhttp3.OkHttpClient
import pe.edu.cibertec.appgasmapocho.retrofit.api.IGasMapochoService
import pe.edu.cibertec.appgasmapocho.util.Constantes
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

// Singleton que arma Retrofit una sola vez y nos da el servicio listo para usar.
object ClienteRetrofit {

    private var okHttpClient = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private fun buildRetrofit() = Retrofit.Builder()
        .baseUrl(Constantes.URL_API_BASE)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val gasMapochoService: IGasMapochoService by lazy {
        buildRetrofit().create(IGasMapochoService::class.java)
    }
}

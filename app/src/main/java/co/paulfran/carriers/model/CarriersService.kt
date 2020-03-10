package co.paulfran.carriers.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CarriersService {

    private val BASE_URL = "http://nodeapidemo.xyz/"

    fun getCarriersService(): CarriersApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CarriersApi::class.java)
    }

}
package co.paulfran.carriers.model

import retrofit2.Response
import retrofit2.http.GET

interface CarriersApi {

    @GET("api/v1/carriers")
    suspend fun getCarriers(): Response<List<Carrier>>
}
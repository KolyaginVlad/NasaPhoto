package ru.same.nasaphoto

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import ru.same.nasaphoto.api.PostNasa

interface NasaApi {
    @GET("/mars-photos/api/v1/rovers/curiosity/photos")
    fun getData(
        @Query("earth_date") date: String,
        @Query("camera") camera: String?,
        @Query("api_key") key: String?
    ): Call<PostNasa?>?
    companion object Factory{
        fun create():NasaApi{
           val retrofit = Retrofit.Builder()
                .baseUrl("https://api.nasa.gov")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(NasaApi::class.java)
        }
    }
}
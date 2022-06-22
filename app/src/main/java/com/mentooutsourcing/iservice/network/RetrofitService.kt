package com.mentooutsourcing.iservice.network

import com.mentooutsourcing.iservice.model.comanda.ComandaItem
import com.mentooutsourcing.iservice.model.mesa.Mesa
import com.mentooutsourcing.iservice.model.produto.Produto
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

const val BASE_URL = "http://10.0.0.2:8080/"

interface RetrofitService {

    @GET("view_produtos")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun getProdutos(): Call<List<Produto>>

    @GET("view_mesas")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun getMesas(): Call<List<Mesa>>

    @GET("mesa")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun leMesa(@Query("idmesa") mesaId: String): Call<List<Mesa>>

    @GET("details")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun getComandaItem(@Query("ordernumber") comandaId: String): Call<List<ComandaItem>>

    @POST("details")
    @Headers("Accept:application/json","Content-Type:application/json")
    fun incluirComandaItem(@Body comandaItem: ComandaItem): Call<ComandaItem>

    companion object {

        private val retrofitService: RetrofitService by lazy {

            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder()
            client.addInterceptor(logging)

            val retrofit =  Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            retrofit.create(RetrofitService::class.java)

        }

        fun getInstance() : RetrofitService {
            return retrofitService
        }
    }
}
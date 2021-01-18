package com.example.odooapitest.api


import com.example.odooapitest.entity.customer.GetCustomerResponse
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST


interface  PythonApiService {
    @POST("jsonrpc/")
    fun getUserData(
        @Body  data:RequestBody
    ):Call<GetCustomerResponse>

    companion object {
        private const val BASE_URL = "http://103.116.190.39:8080/"

        fun create(): PythonApiService {
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PythonApiService::class.java)
        }
    }
}
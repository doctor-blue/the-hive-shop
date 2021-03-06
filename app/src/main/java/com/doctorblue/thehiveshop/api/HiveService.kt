package com.doctorblue.thehiveshop.api

import com.doctorblue.thehiveshop.model.Product
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface HiveService {

    @GET("/products/page&per_page")
    suspend fun getAllProducts(
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): List<Product>


    companion object {
        private const val BASE_URL = "http://0.0.0.0:8080"
        fun create(): HiveService =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build().create(HiveService::class.java)
    }

}
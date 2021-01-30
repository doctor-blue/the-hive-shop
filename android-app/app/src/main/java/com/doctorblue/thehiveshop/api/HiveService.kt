package com.doctorblue.thehiveshop.api

import com.doctorblue.thehiveshop.model.Product
import com.doctorblue.thehiveshop.model.UserModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface HiveService {

    @POST("/authentication/signup")
    suspend fun signUp(@Body user: UserModel)

    @POST("/authentication/signin")
    suspend fun signIn(@Body user: UserModel) : UserModel

    @GET("/products")
    suspend fun getAllProduct(): List<Product>

    @PUT("/authentication/profile")
    suspend fun updateProfile(@Body user: UserModel) : UserModel

    companion object {
        private const val BASE_URL = "http://192.168.15.105:8080"

        fun create(): HiveService = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build().create(HiveService::class.java)

    }

}
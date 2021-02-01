package com.doctorblue.thehiveshop.api

import com.doctorblue.thehiveshop.data.CartRequest
import com.doctorblue.thehiveshop.data.CartResponse
import com.doctorblue.thehiveshop.model.Product
import com.doctorblue.thehiveshop.model.UserModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface HiveService {

    @POST("/authentication/signup")
    suspend fun signUp(@Body user: UserModel)

    @POST("/authentication/signin")
    suspend fun signIn(@Body user: UserModel): UserModel

    @GET("/products")
    suspend fun getAllProduct(): List<Product>

    @PUT("/authentication/profile")
    suspend fun updateProfile(@Body user: UserModel): UserModel

    @POST("/cart")
    suspend fun addProductToCart(@Body request: CartRequest)

    @GET("/cart/{email}")
    suspend fun getCart(
        @Path("email") email: String
    ): CartResponse

    @PATCH("/cart")
    suspend fun updateItemAmount(@Body request: CartRequest)

    @PUT("/cart")
    suspend fun deleteItem(@Body request: CartRequest)

    companion object {
        private const val BASE_URL = "http://192.168.15.105:8080"

        fun create(): HiveService = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(HiveService::class.java)

    }

}
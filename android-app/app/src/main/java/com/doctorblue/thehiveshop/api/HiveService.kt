package com.doctorblue.thehiveshop.api

import com.doctorblue.thehiveshop.model.User
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface HiveService {

    @POST("/authentication/signup")
    suspend fun signUp(@Body user: User)

    @POST("/authentication/signin")
    suspend fun signIn(@Body user: User) : User

    companion object {
        private const val BASE_URL = "http://192.168.15.105:8080"

        fun create(): HiveService = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build().create(HiveService::class.java)

    }

}
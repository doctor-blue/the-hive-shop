package com.doctorblue.thehiveshop.data

import com.doctorblue.thehiveshop.api.HiveService
import com.doctorblue.thehiveshop.model.User

class AuthenticationRepository(private val service: HiveService) {

    suspend fun signUp(user: User) = service.signUp(user)

    suspend fun signIn(user: User) = service.signIn(user)

}
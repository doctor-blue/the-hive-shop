package com.doctorblue.thehiveshop.data

import com.doctorblue.thehiveshop.api.HiveService
import com.doctorblue.thehiveshop.model.UserModel

class AuthenticationRepository(private val service: HiveService) {

    suspend fun signUp(user: UserModel) = service.signUp(user)

    suspend fun signIn(user: UserModel) = service.signIn(user)

}
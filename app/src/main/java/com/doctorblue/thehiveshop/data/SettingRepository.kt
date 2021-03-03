package com.doctorblue.thehiveshop.data

import com.doctorblue.thehiveshop.api.HiveService
import com.doctorblue.thehiveshop.model.UserModel

class SettingRepository(private val service: HiveService) {

    suspend fun updateProfile(user: UserModel) = service.updateProfile(user)

}
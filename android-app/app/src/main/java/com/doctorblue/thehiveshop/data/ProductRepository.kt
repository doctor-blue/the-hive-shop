package com.doctorblue.thehiveshop.data

import com.doctorblue.thehiveshop.api.HiveService
import com.doctorblue.thehiveshop.model.User

class ProductRepository(private val service: HiveService) {

    suspend fun getAllProduct() = service.getAllProduct()
}
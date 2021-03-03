package com.doctorblue.thehiveshop.data

import com.doctorblue.thehiveshop.api.HiveService

class ProductRepository(private val service: HiveService) {

    suspend fun getAllProduct() = service.getAllProduct()
}
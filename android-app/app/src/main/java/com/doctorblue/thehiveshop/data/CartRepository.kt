package com.doctorblue.thehiveshop.data

import com.doctorblue.thehiveshop.api.HiveService
import com.doctorblue.thehiveshop.model.UserModel

class CartRepository(private val service: HiveService) {

    suspend fun addProductToCart(request: CartRequest) = service.addProductToCart(request)

    suspend fun getCart(userModel: UserModel) = service.getCart()
}
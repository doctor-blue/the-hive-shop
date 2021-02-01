package com.doctorblue.thehiveshop.data

import com.doctorblue.thehiveshop.api.HiveService

class CartRepository(private val service: HiveService) {

    suspend fun addProductToCart(request: CartRequest) = service.addProductToCart(request)

    suspend fun getCart() = service.getCart(User.email)

    suspend fun updateItemAmount(request: CartRequest) = service.updateItemAmount(request)

    suspend fun deleteItem(request: CartRequest) = service.deleteItem(request)
}
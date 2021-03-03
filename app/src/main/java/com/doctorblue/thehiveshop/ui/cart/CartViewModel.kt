package com.doctorblue.thehiveshop.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.doctorblue.thehiveshop.data.CartRepository
import com.doctorblue.thehiveshop.data.CartRequest
import com.doctorblue.thehiveshop.utils.Resource
import kotlinx.coroutines.Dispatchers

class CartViewModel(private val cartRepository: CartRepository) : ViewModel() {

    fun addProductToCart(request: CartRequest) = liveData(Dispatchers.IO) {
        emit(Resource.Loading(null))
        try {
            emit(Resource.Success(cartRepository.addProductToCart(request)))
        } catch (e: Exception) {
            emit(Resource.Error(null, e.message ?: "Error"))
        }
    }

    fun getCart() = liveData(Dispatchers.IO) {
        emit(Resource.Loading(null))
        try {
            emit(Resource.Success(cartRepository.getCart().items))
        } catch (e: Exception) {
            emit(Resource.Error(null, e.message ?: "Error"))
        }
    }

    fun updateItemAmount(request: CartRequest) = liveData(Dispatchers.IO) {
        emit(Resource.Loading(null))
        try {
            emit(Resource.Success(cartRepository.updateItemAmount(request)))
        } catch (e: Exception) {
            emit(Resource.Error(e, e.message ?: "Error"))
        }
    }

    fun deleteItem(request: CartRequest) = liveData(Dispatchers.IO) {
        emit(Resource.Loading(null))
        try {
            emit(Resource.Success(cartRepository.deleteItem(request)))
        } catch (e: Exception) {
            emit(Resource.Error(e, e.message ?: "Error"))
        }
    }
}
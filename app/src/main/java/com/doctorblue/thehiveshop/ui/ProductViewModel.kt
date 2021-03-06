package com.doctorblue.thehiveshop.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.doctorblue.thehiveshop.data.ProductRepository
import com.doctorblue.thehiveshop.model.Product
import kotlinx.coroutines.flow.Flow

class ProductViewModel : ViewModel() {

    private val productRepository = ProductRepository()

    fun getAllProduct(): Flow<PagingData<Product>> {
        return productRepository.getAllProducts().cachedIn(viewModelScope)
    }


    class ProductViewModelFactory() : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
                return ProductViewModel() as T
            }
            throw IllegalArgumentException("Unable construct viewmodel")
        }

    }
}
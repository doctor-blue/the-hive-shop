package com.doctorblue.thehiveshop.ui.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.doctorblue.thehiveshop.data.ProductRepository

class ProductViewModelFactory(
    private val productRepository: ProductRepository,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductViewModel(productRepository) as T
        }

        throw IllegalArgumentException("Unable construct viewModel")
    }

}
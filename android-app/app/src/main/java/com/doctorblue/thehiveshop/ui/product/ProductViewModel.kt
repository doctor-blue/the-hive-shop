package com.doctorblue.thehiveshop.ui.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.doctorblue.thehiveshop.data.ProductRepository
import com.doctorblue.thehiveshop.utils.Resource
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import java.io.IOException

class ProductViewModel(private val productRepository: ProductRepository) : ViewModel() {

    fun getAllProduct() = liveData(Dispatchers.IO) {
        emit(Resource.Loading(null))
        try {
            emit(Resource.Success(productRepository.getAllProduct()))
        } catch (ex: IOException) {
            emit(Resource.Error(null, ex.message ?: "Error"))
        } catch (ex: HttpException) {
            emit(Resource.Error(null, ex.message ?: "Error"))
        }
    }
}
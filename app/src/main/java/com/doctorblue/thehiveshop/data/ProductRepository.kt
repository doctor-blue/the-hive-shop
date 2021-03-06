package com.doctorblue.thehiveshop.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.doctorblue.thehiveshop.api.HiveService
import com.doctorblue.thehiveshop.model.Product
import kotlinx.coroutines.flow.Flow

class ProductRepository {

    fun getAllProducts(): Flow<PagingData<Product>> = Pager(
        config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
        pagingSourceFactory = { ProductPagingSource(HiveService.create()) }
    ).flow

    companion object {
        const val NETWORK_PAGE_SIZE = 10
    }
}
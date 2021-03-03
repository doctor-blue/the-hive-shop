package com.doctorblue.thehiveshop.data

import com.doctorblue.thehiveshop.model.ItemInCart

data class CartResponse(
    val email:String,
    val items:List<ItemInCart>
)
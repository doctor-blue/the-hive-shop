package com.doctorblue.thehiveshop.data

import com.doctorblue.thehiveshop.model.ItemInCart
import com.google.gson.annotations.SerializedName

data class CartRequest(
    @field:SerializedName("email") val email: String,
    @field:SerializedName("item") val item: ItemInCart
)
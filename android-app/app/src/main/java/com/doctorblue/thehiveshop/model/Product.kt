package com.doctorblue.thehiveshop.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Product(
    @field:SerializedName("id") val id: String,
    @field:SerializedName("title") var title: String,
    @field:SerializedName("url") var url: String,
    @field:SerializedName("price") var price: Float,
    @field:SerializedName("description") var description: String
):Serializable
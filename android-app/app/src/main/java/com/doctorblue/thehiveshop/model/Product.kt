package com.doctorblue.thehiveshop.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

open class Product(
    @field:SerializedName("id") open val id: String,
    @field:SerializedName("title") open val title: String,
    @field:SerializedName("url") open val url: String,
    @field:SerializedName("price") open val price: Float,
    @field:SerializedName("description") open val description: String
):Serializable
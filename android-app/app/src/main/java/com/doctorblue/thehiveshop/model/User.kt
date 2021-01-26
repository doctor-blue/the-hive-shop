package com.doctorblue.thehiveshop.model

import com.google.gson.annotations.SerializedName

data class User(
    @field:SerializedName("email") var email: String,
    @field:SerializedName("password") var password: String,
    @field:SerializedName("date_of_birth") var dateOfBirth: String,
    @field:SerializedName("is_male") var isMale: Boolean,
    @field:SerializedName("phone_number") var phoneNumber: String,
    @field:SerializedName("address") var address: String
) {

}
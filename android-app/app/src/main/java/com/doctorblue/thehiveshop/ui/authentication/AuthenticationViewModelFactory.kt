package com.doctorblue.thehiveshop.ui.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.doctorblue.thehiveshop.data.AuthenticationRepository
import java.lang.IllegalArgumentException

class AuthenticationViewModelFactory(private val authenticationRepository: AuthenticationRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AuthenticationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthenticationViewModel(authenticationRepository) as T
        }

        throw IllegalArgumentException("Unable construct viewModel")
    }

}
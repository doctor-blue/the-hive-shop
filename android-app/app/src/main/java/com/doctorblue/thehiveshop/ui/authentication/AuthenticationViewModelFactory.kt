package com.doctorblue.thehiveshop.ui.authentication

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.doctorblue.thehiveshop.data.AuthenticationRepository

class AuthenticationViewModelFactory(
    private val authenticationRepository: AuthenticationRepository,
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthenticationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthenticationViewModel(authenticationRepository, context) as T
        }

        throw IllegalArgumentException("Unable construct viewModel")
    }

}
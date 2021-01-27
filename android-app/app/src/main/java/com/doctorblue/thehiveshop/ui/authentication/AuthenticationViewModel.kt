package com.doctorblue.thehiveshop.ui.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.doctorblue.thehiveshop.data.AuthenticationRepository
import com.doctorblue.thehiveshop.model.User
import com.doctorblue.thehiveshop.utils.Resource
import kotlinx.coroutines.Dispatchers

class AuthenticationViewModel(private val authenticationRepository: AuthenticationRepository) :
    ViewModel() {

    fun signUp(user: User) = liveData(Dispatchers.IO) {
        emit(Resource.Loading(null))
        try {
            emit(Resource.Success(authenticationRepository.signUp(user)))
        } catch (ex: Exception) {
            emit(Resource.Error(null, ex.message ?: "Error!!!"))
        }
    }
    fun signIn(user: User) = liveData(Dispatchers.IO) {
        emit(Resource.Loading(null))
        try {
            emit(Resource.Success(authenticationRepository.signIn(user)))
        } catch (ex: Exception) {
            emit(Resource.Error(null, ex.message ?: "Error!!!"))
        }
    }
}
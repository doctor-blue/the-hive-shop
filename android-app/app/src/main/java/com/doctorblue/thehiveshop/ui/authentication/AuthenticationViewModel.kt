package com.doctorblue.thehiveshop.ui.authentication

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.doctorblue.thehiveshop.R
import com.doctorblue.thehiveshop.data.AuthenticationRepository
import com.doctorblue.thehiveshop.model.User
import com.doctorblue.thehiveshop.utils.Resource
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import java.io.IOException

class AuthenticationViewModel(
    private val authenticationRepository: AuthenticationRepository,
    private val context: Context
) :
    ViewModel() {

    fun signUp(user: User) = liveData(Dispatchers.IO) {
        emit(Resource.Loading(null))
        try {
            emit(Resource.Success(authenticationRepository.signUp(user)))
        } catch (ex: IOException) {
            emit(Resource.Error(null, context.resources.getString(R.string.connect_failed)))
        } catch (ex: HttpException) {
            emit(Resource.Error(null, context.resources.getString(R.string.coincide_email)))
        }
    }

    fun signIn(user: User) = liveData(Dispatchers.IO) {
        emit(Resource.Loading(null))
        try {
            emit(Resource.Success(authenticationRepository.signIn(user)))
        } catch (ex: IOException) {
            emit(Resource.Error(null, context.resources.getString(R.string.connect_failed)))
        } catch (ex: HttpException) {
            emit(Resource.Error(null, context.resources.getString(R.string.login_failed)))
        }
    }
}
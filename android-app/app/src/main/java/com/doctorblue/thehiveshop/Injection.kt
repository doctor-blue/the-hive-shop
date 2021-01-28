package com.doctorblue.thehiveshop

import android.content.Context
import com.doctorblue.thehiveshop.api.HiveService
import com.doctorblue.thehiveshop.data.AuthenticationRepository
import com.doctorblue.thehiveshop.ui.authentication.AuthenticationViewModelFactory

object Injection {

    private fun provideAuthenticationRepo() =
        AuthenticationRepository(HiveService.create())

    fun provideAuthenViewModelFactory(context: Context) = AuthenticationViewModelFactory(
        provideAuthenticationRepo(), context
    )

}
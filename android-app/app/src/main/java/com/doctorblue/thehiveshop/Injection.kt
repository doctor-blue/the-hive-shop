package com.doctorblue.thehiveshop

import com.doctorblue.thehiveshop.api.HiveService
import com.doctorblue.thehiveshop.data.AuthenticationRepository
import com.doctorblue.thehiveshop.ui.authentication.AuthenticationViewModelFactory

object Injection {

    private fun provideAuthenticationRepo() =
        AuthenticationRepository(HiveService.create())

    fun provideAuthenViewModelFactory() = AuthenticationViewModelFactory(
        provideAuthenticationRepo()
    )

}
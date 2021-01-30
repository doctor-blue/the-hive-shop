package com.doctorblue.thehiveshop

import android.content.Context
import com.doctorblue.thehiveshop.api.HiveService
import com.doctorblue.thehiveshop.data.AuthenticationRepository
import com.doctorblue.thehiveshop.data.ProductRepository
import com.doctorblue.thehiveshop.data.SettingRepository
import com.doctorblue.thehiveshop.ui.authentication.AuthenticationViewModelFactory
import com.doctorblue.thehiveshop.ui.product.ProductViewModelFactory
import com.doctorblue.thehiveshop.ui.settings.SettingViewModelFactory

object Injection {

    private fun provideAuthenticationRepo() =
        AuthenticationRepository(HiveService.create())

    private fun provideProductRepository() =
        ProductRepository(HiveService.create())

    private fun provideSettingRepository() =
        SettingRepository(HiveService.create())


    fun provideAuthenViewModelFactory(context: Context) = AuthenticationViewModelFactory(
        provideAuthenticationRepo(), context
    )

    fun provideProductViewModelFactory() = ProductViewModelFactory(
        provideProductRepository()
    )

    fun provideSettingViewModelFactory(context: Context) = SettingViewModelFactory(
        provideSettingRepository(), context
    )

}
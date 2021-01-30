package com.doctorblue.thehiveshop.ui.settings

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.doctorblue.thehiveshop.data.SettingRepository
import com.doctorblue.thehiveshop.ui.authentication.AuthenticationViewModel

class SettingViewModelFactory(
    private val settingRepository: SettingRepository,
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SettingViewModel(settingRepository, context) as T
        }

        throw IllegalArgumentException("Unable construct viewModel")
    }


}
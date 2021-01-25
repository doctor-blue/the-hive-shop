package com.doctorblue.thehiveshop.ui.settings

import android.os.Bundle
import android.view.View
import com.doctorblue.thehiveshop.R
import com.doctorblue.thehiveshop.base.BaseFragment
import com.doctorblue.thehiveshop.databinding.FragmentSettingsBinding

class SettingsFragment : BaseFragment() {

    private val binding = getViewBinding() as FragmentSettingsBinding

    override fun getLayoutId(): Int = R.layout.fragment_settings

    override fun initControls(view: View, savedInstanceState: Bundle?) {
    }

    override fun initEvents() {
    }

}
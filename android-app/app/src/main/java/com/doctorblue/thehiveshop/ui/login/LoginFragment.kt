package com.doctorblue.thehiveshop.ui.login

import android.os.Bundle
import android.view.View
import com.doctorblue.thehiveshop.R
import com.doctorblue.thehiveshop.base.BaseFragment
import com.doctorblue.thehiveshop.databinding.FragmentLoginBinding


class LoginFragment : BaseFragment() {

    private val binding = getViewBinding() as FragmentLoginBinding

    override fun getLayoutId(): Int = R.layout.fragment_login

    override fun initControls(view: View, savedInstanceState: Bundle?) {

    }

    override fun initEvents() {

    }

}
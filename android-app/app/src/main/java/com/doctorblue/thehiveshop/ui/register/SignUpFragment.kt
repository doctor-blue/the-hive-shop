package com.doctorblue.thehiveshop.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.doctorblue.thehiveshop.R
import com.doctorblue.thehiveshop.base.BaseFragment
import com.doctorblue.thehiveshop.databinding.FragmentLoginBinding
import com.doctorblue.thehiveshop.databinding.FragmentSignupBinding

class SignUpFragment : BaseFragment() {

    private val binding by lazy {
        getViewBinding() as FragmentSignupBinding
    }

    private val controller by lazy {
        findNavController()
    }

    override fun getLayoutId(): Int = R.layout.fragment_signup

    override fun initControls(view: View, savedInstanceState: Bundle?) {

    }

    override fun initEvents() {
        binding.btnBackToLogin.setOnClickListener {
            controller.popBackStack()
        }
    }

}
package com.doctorblue.thehiveshop.ui.register

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.doctorblue.thehiveshop.R
import com.doctorblue.thehiveshop.base.BaseFragment
import com.doctorblue.thehiveshop.databinding.FragmentSignupBinding

class SignUpFragment : BaseFragment() {

    private val binding: FragmentSignupBinding
        get() = (getViewBinding() as FragmentSignupBinding)

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
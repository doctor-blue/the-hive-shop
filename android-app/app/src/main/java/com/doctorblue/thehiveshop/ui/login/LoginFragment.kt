package com.doctorblue.thehiveshop.ui.login

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.doctorblue.thehiveshop.R
import com.doctorblue.thehiveshop.base.BaseFragment
import com.doctorblue.thehiveshop.databinding.FragmentLoginBinding


class LoginFragment : BaseFragment() {

    private val binding: FragmentLoginBinding
        get() = (getViewBinding() as FragmentLoginBinding)


    private val controller by lazy {
        findNavController()
    }

    override fun getLayoutId(): Int = R.layout.fragment_login

    override fun initControls(view: View, savedInstanceState: Bundle?) {

    }

    override fun initEvents() {
        binding.btnRegister.setOnClickListener {
            controller.navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        binding.btnSignIn.setOnClickListener {
            controller.navigate(R.id.action_loginFragment_to_productFragment)
        }
    }

}
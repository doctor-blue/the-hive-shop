package com.doctorblue.thehiveshop.ui.register

import android.os.Bundle
import android.view.View
import com.doctorblue.thehiveshop.R
import com.doctorblue.thehiveshop.base.BaseActivity
import com.doctorblue.thehiveshop.databinding.ActivitySignupBinding

class SignUpActivity : BaseActivity() {

    private val binding: ActivitySignupBinding
        get() = (getViewBinding() as ActivitySignupBinding)

    override fun getLayoutId(): Int = R.layout.activity_signup

    override fun initControls(savedInstanceState: Bundle?) {

    }

    override fun initEvents() {
        binding.btnBackToLogin.setOnClickListener {
            finish()
        }

        binding.btnRegister.setOnClickListener {
            finish()
        }
    }

}
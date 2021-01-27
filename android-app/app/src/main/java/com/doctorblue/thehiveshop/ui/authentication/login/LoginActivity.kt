package com.doctorblue.thehiveshop.ui.authentication.login

import android.content.Intent
import android.os.Bundle
import com.doctorblue.thehiveshop.MainActivity
import com.doctorblue.thehiveshop.R
import com.doctorblue.thehiveshop.base.BaseActivity
import com.doctorblue.thehiveshop.databinding.ActivityLoginBinding
import com.doctorblue.thehiveshop.ui.authentication.register.SignUpActivity


class LoginActivity : BaseActivity() {

    private val binding: ActivityLoginBinding
        get() = (getViewBinding() as ActivityLoginBinding)

    override fun getLayoutId(): Int = R.layout.activity_login

    override fun initControls(savedInstanceState: Bundle?) {

    }

    override fun initEvents() {
        binding.btnRegister.setOnClickListener {
            val intent: Intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.btnSignIn.setOnClickListener {
            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}
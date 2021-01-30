package com.doctorblue.thehiveshop.ui.authentication.register

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.doctorblue.thehiveshop.Injection
import com.doctorblue.thehiveshop.R
import com.doctorblue.thehiveshop.base.BaseActivity
import com.doctorblue.thehiveshop.databinding.ActivitySignupBinding
import com.doctorblue.thehiveshop.model.UserModel
import com.doctorblue.thehiveshop.ui.authentication.AuthenticationViewModel
import com.doctorblue.thehiveshop.utils.Resource

class SignUpActivity : BaseActivity() {

    private val binding: ActivitySignupBinding
        get() = (getViewBinding() as ActivitySignupBinding)

    private val authenticationViewModel by lazy {
        ViewModelProvider(
            this,
            Injection.provideAuthenViewModelFactory(this)
        )[AuthenticationViewModel::class.java]
    }

    override fun getLayoutId(): Int = R.layout.activity_signup

    override fun initControls(savedInstanceState: Bundle?) {

    }

    override fun initEvents() {
        binding.btnBackToLogin.setOnClickListener {
            finish()
        }

        binding.btnRegister.setOnClickListener {
            register()
        }

        onTextChanged()
    }

    private fun onTextChanged() {
        binding.edtEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.edtEmail.text.toString().isNotEmpty()) {
                    binding.textInputEmail.error = null
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        binding.edtPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.edtPassword.text.toString().isNotEmpty()) {
                    binding.textInputPassword.error = null
                    binding.textInputPassword.boxStrokeColor =
                        checkStrengthPass(binding.edtPassword.text.toString())
                } else
                    binding.textInputPassword.boxStrokeColor =
                        resources.getColor(R.color.colorPrimaryDark)
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        binding.edtRePassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.edtRePassword.text.toString().isNotEmpty()) {
                    binding.textInputRetypePass.error = null
                }

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    private fun register() {

        var validUser = true

        if (binding.edtEmail.text.toString().isEmpty()) {
            binding.textInputEmail.error = resources.getString(R.string.empty_error)
            validUser =false
        } else if (!isValidEmail(binding.edtEmail.text.toString())) {
            binding.textInputEmail.error = resources.getString(R.string.invalid_email)
            validUser = false
        }

        if (binding.edtPassword.text.toString().isEmpty()) {
            binding.textInputPassword.error = resources.getString(R.string.empty_error)
            validUser = false
        } else if (binding.edtPassword.text.toString().length < 8 || binding.edtPassword.text.toString().length > 20) {
            binding.textInputPassword.error = resources.getString(R.string.invalid_pass_condition)
            validUser = false
        }

        if (binding.edtRePassword.text.toString().isEmpty()) {
            binding.textInputRetypePass.error = resources.getString(R.string.empty_error)
            validUser = false
        } else if (binding.edtPassword.text.toString() != binding.edtRePassword.text.toString()) {
            binding.textInputRetypePass.error = resources.getString(R.string.not_match_pass)
            validUser = false
        }

        if (validUser) {
            authenticationViewModel.signUp(
                UserModel(
                    binding.edtEmail.text.toString(),
                    binding.edtPassword.text.toString(),
                    "",
                    true,
                    "",
                    "",
                    false
                )
            ).observeForever {
                it?.let { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            binding.pbLogin.visibility = View.GONE
                            finish()
                        }

                        is Resource.Loading -> {
                            binding.pbLogin.visibility = View.VISIBLE
                        }

                        is Resource.Error -> {
                            Toast.makeText(
                                this,
                                resource.message,
                                Toast.LENGTH_SHORT
                            ).show()
                            binding.pbLogin.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

    private fun checkStrengthPass(pass: String): Int {
        var chars = pass.toCharArray()
        var containDigit: Boolean = false
        var containUpperCase: Boolean = false
        var containLowerCase: Boolean = false

        for (i in chars.indices) {
            if (chars[i].isDigit()) containDigit = true
            if (chars[i].isUpperCase()) containUpperCase = true
            if (chars[i].isLowerCase()) containLowerCase = true
        }

        return if (pass.length in 8..20 && !containDigit && containUpperCase && containLowerCase) {
            resources.getColor(R.color.medium_pass_color)
        } else if (pass.length in 8..20 && containDigit && containUpperCase && containLowerCase) {
            resources.getColor(R.color.strong_pass_color)
        } else {
            resources.getColor(R.color.weak_pass_color)
        }
    }

    private fun isValidEmail(target: CharSequence?): Boolean {
        return if (target == null) false
        else Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

}
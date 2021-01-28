package com.doctorblue.thehiveshop.ui.authentication.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.doctorblue.thehiveshop.Injection
import com.doctorblue.thehiveshop.MainActivity
import com.doctorblue.thehiveshop.R
import com.doctorblue.thehiveshop.base.BaseActivity
import com.doctorblue.thehiveshop.databinding.ActivityLoginBinding
import com.doctorblue.thehiveshop.model.User
import com.doctorblue.thehiveshop.ui.authentication.AuthenticationViewModel
import com.doctorblue.thehiveshop.ui.authentication.register.SignUpActivity
import com.doctorblue.thehiveshop.utils.Resource
import java.io.Serializable


class LoginActivity : BaseActivity() {

    private val binding: ActivityLoginBinding
        get() = (getViewBinding() as ActivityLoginBinding)

    private val authenticationViewModel by lazy {
        ViewModelProvider(
            this,
            Injection.provideAuthenViewModelFactory(this)
        )[AuthenticationViewModel::class.java]
    }

    override fun getLayoutId(): Int = R.layout.activity_login

    override fun initControls(savedInstanceState: Bundle?) {

    }

    override fun initEvents() {
        binding.btnRegister.setOnClickListener {
            val intent: Intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.btnSignIn.setOnClickListener {
            login()
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
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

    }

    private fun login() {
        if (binding.edtEmail.text.toString().isEmpty()) {
            binding.textInputEmail.error = resources.getString(R.string.empty_error)
        }

        if (binding.edtPassword.text.toString().isEmpty()) {
            binding.textInputPassword.error = resources.getString(R.string.empty_error)
        }

        if (binding.edtPassword.text.toString()
                .isEmpty() && !isValidEmail(binding.edtEmail.text.toString()) && binding.edtEmail.text.toString()
                .isNotEmpty()
        ) {
            binding.textInputEmail.error = resources.getString(R.string.invalid_email)
        }

        if (binding.edtEmail.text.toString().isNotEmpty() && binding.edtPassword.text.toString()
                .isNotEmpty()
        ) {
            if (!isValidEmail(binding.edtEmail.text.toString())) {
                binding.textInputEmail.error = resources.getString(R.string.invalid_email)
            } else {
                authenticationViewModel.signIn(
                    User(
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
                                val intent = Intent(this, MainActivity::class.java)
                                intent.putExtra("USER", resource.data as Serializable)
                                binding.pbLogin.visibility = View.GONE
                                startActivity(intent)
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

    }

    private fun isValidEmail(target: CharSequence?): Boolean {
        return if (target == null) false
        else Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

}
package com.doctorblue.thehiveshop.ui.authentication.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import kotlinx.android.synthetic.main.activity_login.*
import java.io.Serializable


class LoginActivity : BaseActivity() {

    private val binding: ActivityLoginBinding
        get() = (getViewBinding() as ActivityLoginBinding)

    private val authenticationViewModel by lazy {
        ViewModelProvider(
            this,
            Injection.provideAuthenViewModelFactory()
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
        edt_email.addTextChangedListener(object : TextWatcher {
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

        edt_password.addTextChangedListener(object : TextWatcher {
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

        if (binding.edtEmail.text.toString().isNotEmpty() && binding.edtPassword.text.toString()
                .isNotEmpty()
        ) {
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
                            startActivity(intent)
                            finish()
                        }

                        is Resource.Loading -> {

                        }

                        is Resource.Error -> {
                            Toast.makeText(
                                this,
                                resources.getString(R.string.login_failed),
//                                resource.message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }

    }

}
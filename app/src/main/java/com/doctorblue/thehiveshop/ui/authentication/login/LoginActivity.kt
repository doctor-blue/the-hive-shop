package com.doctorblue.thehiveshop.ui.authentication.login

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.doctorblue.thehiveshop.Injection
import com.doctorblue.thehiveshop.MainActivity
import com.doctorblue.thehiveshop.R
import com.doctorblue.thehiveshop.base.BaseActivity
import com.doctorblue.thehiveshop.data.User
import com.doctorblue.thehiveshop.databinding.ActivityLoginBinding
import com.doctorblue.thehiveshop.model.UserModel
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

    private val dialogLoading by lazy {
        Dialog(this)
    }

    private val sharedPreferences: SharedPreferences by lazy {
        getSharedPreferences("USER_EMAIL", Context.MODE_PRIVATE)
    }

    override fun getLayoutId(): Int = R.layout.activity_login

    override fun initControls(savedInstanceState: Bundle?) {
        if(sharedPreferences.getString("EMAIL", "") != null) {
            binding.edtEmail.setText(sharedPreferences.getString("EMAIL", ""))
        }
        dialogLoading.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogLoading.setCancelable(false)
        dialogLoading.setContentView(R.layout.dialog_loading)
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
        binding.edtEmail.onFocusChangeListener =
            View.OnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    hideKeyboard(v)
                }
            }
        binding.edtPassword.onFocusChangeListener =
            View.OnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    hideKeyboard(v)
                }
            }
    }

    private fun login() {

        var validEmail = true
        var validPass = true

        if (binding.edtEmail.text.toString().isEmpty()) {
            binding.textInputEmail.error = resources.getString(R.string.empty_error)
            validEmail = false
        } else if (!isValidEmail(binding.edtEmail.text.toString())) {
            binding.textInputEmail.error = resources.getString(R.string.invalid_email)
            validEmail = false
        }

        if (binding.edtPassword.text.toString().isEmpty()) {
            binding.textInputPassword.error = resources.getString(R.string.empty_error)
            validPass = false
        }

        if (validEmail && validPass) {
            authenticationViewModel.signIn(
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
                            User.setNewUser(resource.data)
                            val editor: SharedPreferences.Editor = sharedPreferences.edit()
                            editor.putString("EMAIL", User.email)
                            editor.apply()
                            dialogLoading.dismiss()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }

                        is Resource.Loading -> {
                            dialogLoading.show()
                        }

                        is Resource.Error -> {
                            dialogLoading.dismiss()
                            Toast.makeText(
                                this,
                                resource.message,
                                Toast.LENGTH_SHORT
                            ).show()
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
    private fun hideKeyboard(view: View) {
        val inputMethodManager: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
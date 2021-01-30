package com.doctorblue.thehiveshop.ui.settings

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.doctorblue.thehiveshop.Injection
import com.doctorblue.thehiveshop.R
import com.doctorblue.thehiveshop.base.BaseFragment
import com.doctorblue.thehiveshop.data.User
import com.doctorblue.thehiveshop.databinding.FragmentSettingsBinding
import com.doctorblue.thehiveshop.model.UserModel
import com.doctorblue.thehiveshop.ui.authentication.login.LoginActivity
import com.doctorblue.thehiveshop.utils.Resource
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import kotlinx.android.synthetic.main.fragment_settings.*
import java.util.*


class SettingsFragment : BaseFragment() {

    private val binding: FragmentSettingsBinding
        get() = (getViewBinding() as FragmentSettingsBinding)

    private val settingViewModel by lazy {
        ViewModelProvider(
            this,
            Injection.provideSettingViewModelFactory(requireContext())
        )[SettingViewModel::class.java]
    }

    override fun getLayoutId(): Int = R.layout.fragment_settings

    override fun initControls(view: View, savedInstanceState: Bundle?) {
        binding.txtEmail.text = User.email
        binding.txtDob.text = User.dateOfBirth
        binding.txtGender.text = if (User.isMale) "Male" else "Female"
        binding.txtPhoneNumber.text = User.phoneNumber
        binding.txtAddress.text = User.address
    }

    override fun initEvents() {

        binding.itemDob.setOnClickListener {
            val c: Calendar = Calendar.getInstance()

            var year: Int
            var month: Int
            var day: Int

            if (binding.txtDob.text == "") {
                year = c.get(Calendar.YEAR)
                month = c.get(Calendar.MONTH)
                day = c.get(Calendar.DAY_OF_MONTH)
            } else {
                year = c.get(Calendar.YEAR)
                month = c.get(Calendar.MONTH)
                day = c.get(Calendar.DAY_OF_MONTH)
            }

            val datePickerDialog = DatePickerDialog(
                requireContext(), R.style.DialogTheme,
                { view, year, monthOfYear, dayOfMonth ->
                    User.dateOfBirth = dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year
                    // Push to server

                    settingViewModel.updateUser(User.getUserInfo()).observeForever {
                        observeData(it, binding.txtDob, User.dateOfBirth)
                    }
                },
                year, month, day,
            )
            datePickerDialog.show()
        }

        binding.itemGender.setOnClickListener {
            val genderDialog: AlertDialog.Builder = AlertDialog.Builder(
                activity,
                R.style.DialogTheme
            )
            genderDialog.apply {
                setTitle(resources.getString(R.string.gender))
                setItems(R.array.gender) { dialog, which ->
                    var gender: String
                    when (which) {
                        0 -> {
                            User.isMale = true
                            gender = "Male"

                            // Push to server
                            settingViewModel.updateUser(User.getUserInfo()).observeForever {
                                observeData(it, binding.txtGender, gender)
                            }
                        }
                        1 -> {
                            User.isMale = false
                            gender = "Female"
                            // Push to server
                            settingViewModel.updateUser(User.getUserInfo()).observeForever {
                                observeData(it, binding.txtGender, gender)
                            }
                        }
                    }
                    dialog.dismiss()
                }
            }
            genderDialog.create().show()
        }

        binding.itemPhoneNumber.setOnClickListener {
            val dialogTitle = resources.getString(R.string.phone_number)
            showDialog(
                R.layout.dialog_add_text,
                dialogTitle,
                binding.txtPhoneNumber.text.toString(),
                InputType.TYPE_CLASS_PHONE
            ) { edtDialog ->
                User.phoneNumber = edtDialog.text.toString()
                settingViewModel.updateUser(User.getUserInfo()).observeForever {
                    observeData(it, binding.txtPhoneNumber, User.phoneNumber)
                }

            }
        }

        binding.itemAddress.setOnClickListener {
            val dialogTitle = resources.getString(R.string.address)
            showDialog(
                R.layout.dialog_add_text,
                dialogTitle,
                binding.txtAddress.text.toString(),
                InputType.TYPE_CLASS_TEXT
            ) { edtDialog ->
                User.address = edtDialog.text.toString()
                settingViewModel.updateUser(User.getUserInfo()).observeForever {
                    observeData(it, binding.txtAddress, User.address)
                }
            }
        }

        binding.itemChangePass.setOnClickListener {
            val dialogTitle = resources.getString(R.string.change_password)
            showDialog(
                R.layout.dialog_change_pass,
                dialogTitle,
                binding.txtPass.text.toString(),
                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            ) { edtDialog ->
                User.password = edtDialog.text.toString()
                settingViewModel.updateUser(User.getUserInfo()).observeForever {
                    observeData(it, binding.txtAddress, "")
                }
            }
        }

        binding.itemLogout.setOnClickListener {
            val logoutDialog: AlertDialog.Builder = AlertDialog.Builder(
                activity,
                R.style.DialogTheme
            )
            logoutDialog.apply {
                setTitle(resources.getString(R.string.logout))
                setMessage("Are you sure you want to log out?")
                setPositiveButton(R.string.logout) { dialog, id ->
                    User.logOut()
                    startActivity(Intent(requireContext(), LoginActivity::class.java))
                    activity?.finish()
                }
                setNegativeButton(R.string.cancel) { dialog, id ->
                    dialog.dismiss()
                }
            }

            logoutDialog.create().show()
        }

    }

    private fun observeData(resource: Resource<UserModel>, textView: MaterialTextView, data: Any) {
        resource?.let {
            when (it) {
                is Resource.Success -> {
                    textView.text = data.toString()
                    Toast.makeText(
                        requireContext(),
                        resources.getString(R.string.done),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is Resource.Loading -> {

                }

                is Resource.Error -> {
                    Toast.makeText(
                        requireContext(),
                        it.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun showDialog(
        dialogId: Int,
        dialogTitle: String,
        content1: String,
        inputType1: Int,
        onConfirm: (edtDialog: TextInputEditText) -> Unit
    ) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(dialogId)
        val textInputDialog1 = dialog.findViewById<TextInputLayout>(R.id.text_input_dialog_1)
        val edtDialog1 = dialog.findViewById<TextInputEditText>(R.id.edt_dialog_1)
        val title = dialog.findViewById<MaterialTextView>(R.id.txt_dialog_title)
        val btnConfirm = dialog.findViewById(R.id.btn_confirm) as MaterialButton
        val btnCancel = dialog.findViewById(R.id.btn_cancel) as MaterialButton
        if (dialogId == R.layout.dialog_change_pass) {
            val textInputDialog2 = dialog.findViewById<TextInputLayout>(R.id.text_input_dialog_2)
            val edtDialog2 = dialog.findViewById<TextInputEditText>(R.id.edt_dialog_2)
            val textInputDialog3 = dialog.findViewById<TextInputLayout>(R.id.text_input_dialog_3)
            val edtDialog3 = dialog.findViewById<TextInputEditText>(R.id.edt_dialog_3)
            title.text = dialogTitle
            textInputDialog1.hint = resources.getString(R.string.password)
            textInputDialog2.hint = resources.getString(R.string.new_password)
            textInputDialog3.hint = resources.getString(R.string.retype_pasword)

            edtDialog1.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (edtDialog1.text.toString().isNotEmpty()) {
                        textInputDialog1.error = null
                    }
                }

                override fun afterTextChanged(p0: Editable?) {

                }

            })

            edtDialog2.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (edtDialog2.text.toString().isNotEmpty()) {
                        textInputDialog2.error = null
                        textInputDialog2.boxStrokeColor =
                            checkStrengthPass(edtDialog2.text.toString())
                    } else
                        textInputDialog2.boxStrokeColor =
                            resources.getColor(R.color.colorPrimaryDark)
                }

                override fun afterTextChanged(p0: Editable?) {

                }

            })

            edtDialog3.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (edtDialog3.text.toString().isNotEmpty()) {
                        textInputDialog3.error = null
                    }
                }

                override fun afterTextChanged(p0: Editable?) {

                }

            })

            btnConfirm.setOnClickListener {

                var validPass = true

                if (edtDialog1.text.toString().isEmpty()) {
                    textInputDialog1.error = resources.getString(R.string.empty_error)
                    validPass = false
                }

                if (edtDialog2.text.toString().isEmpty()) {
                    textInputDialog2.error = resources.getString(R.string.empty_error)
                    validPass = false
                } else if (edtDialog2.text.toString().length < 8 || edtDialog2.text.toString().length > 20) {
                    textInputDialog2.error = resources.getString(R.string.invalid_pass_condition)
                    validPass = false
                }

                if (edtDialog3.text.toString().isEmpty()) {
                    textInputDialog3.error = resources.getString(R.string.empty_error)
                    validPass = false
                } else if (edtDialog3.text.toString() != edtDialog2.text.toString()) {
                    textInputDialog3.error = resources.getString(R.string.not_match_pass)
                    validPass = false
                }

                if (validPass) {
                    if (User.password == edtDialog1.text.toString()) {
                        onConfirm(edtDialog2)
                        dialog.dismiss()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            resources.getString(R.string.old_pass_wrong),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

        } else {
            title.text = dialogTitle
            textInputDialog1.hint = dialogTitle
            edtDialog1.setText(content1)
            edtDialog1.inputType = inputType1
            btnConfirm.setOnClickListener {
                onConfirm(edtDialog1)
                dialog.dismiss()
            }
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
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

}
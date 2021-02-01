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
import androidx.navigation.fragment.findNavController
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


class SettingsFragment : BaseFragment(), View.OnClickListener {

    private val binding: FragmentSettingsBinding
        get() = (getViewBinding() as FragmentSettingsBinding)

    private val controller by lazy {
        findNavController()
    }

    private val settingViewModel by lazy {
        ViewModelProvider(
            this,
            Injection.provideSettingViewModelFactory(requireContext())
        )[SettingViewModel::class.java]
    }

    private val dialogLoading by lazy {
        Dialog(requireContext())
    }

    override fun getLayoutId(): Int = R.layout.fragment_settings

    override fun initControls(view: View, savedInstanceState: Bundle?) {
        dialogLoading.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogLoading.setCancelable(false)
        dialogLoading.setContentView(R.layout.dialog_loading)
        binding.txtEmail.text = User.email
        binding.txtDob.text = User.dateOfBirth
        binding.txtGender.text = if (User.isMale) "Male" else "Female"
        binding.txtPhoneNumber.text = User.phoneNumber
        binding.txtAddress.text = User.address
    }

    override fun initEvents() {
        binding.itemDob.setOnClickListener(this)
        binding.itemGender.setOnClickListener(this)
        binding.itemPhoneNumber.setOnClickListener(this)
        binding.itemAddress.setOnClickListener(this)
        binding.itemChangePass.setOnClickListener(this)
        binding.itemLogout.setOnClickListener(this)
        toolbar_setting.setNavigationOnClickListener {
            controller.popBackStack()
        }
    }

    private fun observeData(resource: Resource<UserModel>, textView: MaterialTextView, data: Any) {
        resource?.let {
            when (it) {

                is Resource.Success -> {
                    dialogLoading.dismiss()
                    textView.text = data.toString()
                    Toast.makeText(
                        requireContext(),
                        resources.getString(R.string.done),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is Resource.Loading -> {
                    dialogLoading.show()
                }

                is Resource.Error -> {
                    dialogLoading.dismiss()
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

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.item_dob -> {
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
                        User.dateOfBirth =
                            dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year
                        // Push to server

                        settingViewModel.updateUser(User.getUserInfo()).observeForever {
                            observeData(it, binding.txtDob, User.dateOfBirth)
                        }
                    },
                    year, month, day,
                )
                datePickerDialog.show()
            }

            R.id.item_gender -> {
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

            R.id.item_phone_number -> {
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

            R.id.item_address -> {
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

            R.id.item_change_pass -> {
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

            R.id.item_logout -> {
                val dialog = Dialog(requireContext())
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setCancelable(false)
                dialog.setContentView(R.layout.dialog_confirm)
                val title = dialog.findViewById<MaterialTextView>(R.id.txt_dialog_title)
                title.text = resources.getString(R.string.logout)
                val message = dialog.findViewById<MaterialTextView>(R.id.txt_dialog_message)
                message.text = resources.getString(R.string.logout_message)
                val btnConfirm = dialog.findViewById<MaterialButton>(R.id.btn_confirm)
                val btnCancel = dialog.findViewById<MaterialButton>(R.id.btn_cancel)
                btnConfirm.setOnClickListener {
                    User.logOut()
                    startActivity(Intent(requireContext(), LoginActivity::class.java))
                    activity?.finish()
                }

                btnCancel.setOnClickListener {
                    dialog.dismiss()
                }
                dialog.show()
            }
        }
    }

}
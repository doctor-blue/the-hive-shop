package com.doctorblue.thehiveshop.ui.settings

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.doctorblue.thehiveshop.R
import com.doctorblue.thehiveshop.base.BaseFragment
import com.doctorblue.thehiveshop.data.User
import com.doctorblue.thehiveshop.databinding.FragmentSettingsBinding
import com.doctorblue.thehiveshop.ui.authentication.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : BaseFragment() {

    private val binding: FragmentSettingsBinding
        get() = (getViewBinding() as FragmentSettingsBinding)

    override fun getLayoutId(): Int = R.layout.fragment_settings

    override fun initControls(view: View, savedInstanceState: Bundle?) {
        txt_email.text = User.email
        txt_dob.text = User.dateOfBirth
        txt_gender.text = if(User.isMale) "Male" else "Female"
        txt_phone_number.text = User.phoneNumber
        txt_address.text = User.address
    }

    override fun initEvents() {

        binding.itemDob.setOnClickListener {

        }

        binding.itemGender.setOnClickListener {
            val genderDialog: AlertDialog.Builder = AlertDialog.Builder(activity, R.style.DialogTheme)
            genderDialog.apply {
                setTitle(resources.getString(R.string.gender))
                setItems(R.array.gender) { dialog, which ->
                    when (which) {
                        0 -> {
                            Toast.makeText(requireContext(), "Male", Toast.LENGTH_SHORT).show()
                            // Push to server
                        }
                        1 -> {
                            Toast.makeText(requireContext(), "Female", Toast.LENGTH_SHORT).show()
                            // Push to server
                        }
                    }
                    dialog.dismiss()
                }
            }
            genderDialog.create().show()
        }

        binding.itemPhoneNumber.setOnClickListener {

        }

        binding.itemAddress.setOnClickListener {

        }

        binding.itemChangePass.setOnClickListener {

        }

        binding.itemLogout.setOnClickListener {
            val logoutDialog: AlertDialog.Builder = AlertDialog.Builder(activity, R.style.DialogTheme)
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

}
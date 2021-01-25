package com.doctorblue.thehiveshop.ui.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.doctorblue.thehiveshop.R
import com.doctorblue.thehiveshop.base.BaseFragment
import com.doctorblue.thehiveshop.databinding.FragmentCartBinding
import com.doctorblue.thehiveshop.databinding.FragmentLoginBinding

class CartFragment : BaseFragment() {
    private val binding = getViewBinding() as FragmentCartBinding

    override fun getLayoutId(): Int = R.layout.fragment_cart

    override fun initControls(view: View, savedInstanceState: Bundle?) {

    }

    override fun initEvents() {

    }

}
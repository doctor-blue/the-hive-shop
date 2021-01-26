package com.doctorblue.thehiveshop.ui.cart

import android.os.Bundle
import android.view.View
import com.doctorblue.thehiveshop.R
import com.doctorblue.thehiveshop.base.BaseFragment
import com.doctorblue.thehiveshop.databinding.FragmentCartBinding

class CartFragment : BaseFragment() {

    private val binding: FragmentCartBinding
        get() = (getViewBinding() as FragmentCartBinding)


    override fun getLayoutId(): Int = R.layout.fragment_cart

    override fun initControls(view: View, savedInstanceState: Bundle?) {

    }

    override fun initEvents() {

    }

}
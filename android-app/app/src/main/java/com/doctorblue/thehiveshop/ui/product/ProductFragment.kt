package com.doctorblue.thehiveshop.ui.product

import android.os.Bundle
import android.view.View
import com.doctorblue.thehiveshop.R
import com.doctorblue.thehiveshop.base.BaseFragment
import com.doctorblue.thehiveshop.databinding.FragmentProductBinding

class ProductFragment : BaseFragment() {

    private val binding: FragmentProductBinding
        get() = (getViewBinding() as FragmentProductBinding)

    override fun getLayoutId(): Int = R.layout.fragment_product

    override fun initControls(view: View, savedInstanceState: Bundle?) {

    }

    override fun initEvents() {

    }

}
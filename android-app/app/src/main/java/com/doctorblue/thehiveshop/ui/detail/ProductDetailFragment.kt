package com.doctorblue.thehiveshop.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.doctorblue.thehiveshop.R
import com.doctorblue.thehiveshop.base.BaseFragment
import com.doctorblue.thehiveshop.databinding.FragmentLoginBinding
import com.doctorblue.thehiveshop.databinding.FragmentProductDetailBinding

class ProductDetailFragment : BaseFragment() {
    private val binding by lazy {
        getViewBinding() as FragmentProductDetailBinding
    }

    override fun getLayoutId(): Int = R.layout.fragment_product_detail

    override fun initControls(view: View, savedInstanceState: Bundle?) {

    }

    override fun initEvents() {

    }

}